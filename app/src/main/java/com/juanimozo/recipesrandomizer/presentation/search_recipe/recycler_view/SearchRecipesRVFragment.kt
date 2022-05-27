package com.juanimozo.recipesrandomizer.presentation.search_recipe.recycler_view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.juanimozo.recipesrandomizer.R
import com.juanimozo.recipesrandomizer.databinding.FragmentSearchRecipesRvBinding
import com.juanimozo.recipesrandomizer.presentation.search_recipe.SearchRecipesAdapter
import com.juanimozo.recipesrandomizer.presentation.util.InternetConnection
import com.juanimozo.recipesrandomizer.presentation.util.SetAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchRecipesRVFragment : Fragment() {

    private var _binding: FragmentSearchRecipesRvBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchRecipeRVViewModel by viewModels()

    private val args: SearchRecipesRVFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchRecipesRvBinding.inflate(inflater, container, false)

        // Internet Connection
        checkInternetConnection()
        observeInternetConnection()

        val loadingAnimation = SetAnimation(binding.loadingFoodAnimation, R.raw.start_animation)

        val recyclerView = binding.searchRecipesRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val recyclerViewAdapter = SearchRecipesAdapter {
            // When the user select a recipe, get the information and navigate to recipe details
            checkInternetConnection()
            // If there´s internet connection get the selected recipe
            if (viewModel.internetConnection.value) {
                viewModel.getRecipeInformation(it.id)
            } else {
                Snackbar.make(requireView(), R.string.no_internet_connection, Snackbar.LENGTH_SHORT).show()
            }
            observeNewRecipeState()
        }
        recyclerView.adapter = recyclerViewAdapter

        // Only start animation when there are no recipes loaded in viewModel
        if(!viewModel.searchRecipeState.value.areRecipesLoaded) {
            observeState(recyclerViewAdapter, loadingAnimation)
        } else {
            // When recipes are loaded submit the list and hide animation
            recyclerViewAdapter.submitList(viewModel.searchRecipeState.value.recipes)
            binding.loadingFoodAnimation.visibility = View.GONE
            binding.searchRecipesDivider.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Observer of RandomRecipesState in ViewModel
    private fun observeState(rvAdapter: SearchRecipesAdapter, animation: SetAnimation) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchRecipeState.collect {
                // Start loading animation
                animation.startAnimation()
                delay(animation.animation.duration)
                // Stop and hide animation when isLoading = False
                if (it.areRecipesLoaded) {
                    animation.finishAnimation()
                }
                // Submit new list to adapter
                rvAdapter.submitList(it.recipes)
            }
        }
    }

    private fun observeInternetConnection() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.internetConnection.collect { isInternetConnected ->
                if (isInternetConnected) {
                    // Make call to bring list of results
                    viewModel.searchRecipes(
                        query = args.query,
                        cuisine = args.cuisine,
                        diet = args.diet
                    )
                }
            }
        }
    }

    private fun observeNewRecipeState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.newRecipe.collect {
                // Load the new recipe selected from similar recipes
                val action = SearchRecipesRVFragmentDirections.actionSearchRecipesRVFragmentToRecipeDetails(
                    recipe = it
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun checkInternetConnection() {
        val isInternetConnected = InternetConnection(requireContext()).checkInternetConnection()
        if (isInternetConnected) {
            viewModel.handleInternetConnection(isInternetConnected = true)
        } else {
            viewModel.handleInternetConnection(isInternetConnected = false)
        }
    }

}