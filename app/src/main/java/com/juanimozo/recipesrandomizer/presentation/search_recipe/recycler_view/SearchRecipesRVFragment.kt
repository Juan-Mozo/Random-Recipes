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

        // Create a loading animation for the fragment
        val loadingAnimation = SetAnimation(binding.loadingFoodAnimation, R.raw.start_animation)

        // Set RecyclerView
        val recyclerView = binding.searchRecipesRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Set Adapter
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

        // Check if the list of recipes is loaded
        if(viewModel.searchRecipeState.value.areRecipesLoaded) {
            // Submit the current recipe list to adapter
            recyclerViewAdapter.submitList(viewModel.searchRecipeState.value.recipes)
            // Finish animation
            binding.loadingFoodAnimation.visibility = View.GONE
            // Show divider on top of the screen
            binding.searchRecipesDivider.visibility = View.VISIBLE
        } else {
            // Handle loading animation and get new recipes
            observeState(recyclerViewAdapter, loadingAnimation)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Observer of InternetConnection State in ViewModel
    private fun observeInternetConnection() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.internetConnection.collect { isInternetConnected ->
                if (isInternetConnected) {
                    // If internet connection is active then make api call to get recipes
                    viewModel.searchRecipes(
                        query = args.query,
                        cuisine = args.cuisine,
                        diet = args.diet
                    )
                }
            }
        }
    }

    // Observer of RandomRecipesState in ViewModel
    private fun observeState(rvAdapter: SearchRecipesAdapter, animation: SetAnimation) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchRecipeState.collect {
                // Start loading animation
                animation.startAnimation()
                // Give time to show animation
                delay(animation.animation.duration)
                // Check whether the recipes loaded successfully
                if (it.areRecipesLoaded) {
                    // Stop and hide animation
                    animation.finishAnimation()
                }
                // Submit the new list with recipes loaded to adapter
                rvAdapter.submitList(it.recipes)
                // Show divider on top of the screen
                binding.searchRecipesDivider.visibility = View.VISIBLE
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

    // Check status of internet connection and upgrade InternetConnection State in ViewModel
    private fun checkInternetConnection() {
        val isInternetConnected = InternetConnection(requireContext()).checkInternetConnection()
        if (isInternetConnected) {
            // Set internet connection status to true
            viewModel.handleInternetConnection(isInternetConnected = true)
        } else {
            // Set internet connection status to false
            viewModel.handleInternetConnection(isInternetConnected = false)
        }
    }

}