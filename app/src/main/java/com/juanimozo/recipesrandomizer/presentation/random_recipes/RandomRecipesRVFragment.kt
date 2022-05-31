package com.juanimozo.recipesrandomizer.presentation.random_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanimozo.recipesrandomizer.R
import com.juanimozo.recipesrandomizer.databinding.FragmentRandomRecipesRVBinding
import com.juanimozo.recipesrandomizer.presentation.util.InternetConnection
import com.juanimozo.recipesrandomizer.presentation.util.RecipesAdapter
import com.juanimozo.recipesrandomizer.presentation.util.SetAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RandomRecipesRVFragment : Fragment() {

    private var _binding: FragmentRandomRecipesRVBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RandomRecipesRVViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomRecipesRVBinding.inflate(inflater, container, false)

        // Check and upgrade Internet Connection status
        checkInternetConnection()
        observeInternetConnection()

        // Create a loading animation for the fragment
        val loadingAnimation = SetAnimation(binding.loadingFoodAnimation, R.raw.loading_food)

        // Set RecyclerView and Adapter
        val recyclerView = binding.randomRecipesRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val recyclerViewAdapter = RecipesAdapter { recipe ->
            // When the user selects a recipe navigate to RecipeDetailsFragment
            val action = RandomRecipesRVFragmentDirections.actionRandomRecipesRVFragmentToRecipeDetails(
                recipe = recipe
            )
            findNavController().navigate(action)
        }
        recyclerView.adapter = recyclerViewAdapter

        // Check if the list of recipes is loaded
        if(viewModel.randomRecipesState.value.recipesAreLoaded) {
            // Submit the current recipe list to adapter
            recyclerViewAdapter.submitList(viewModel.randomRecipesState.value.recipes)
            // Finish animation
            binding.loadingFoodAnimation.visibility = View.GONE
            // Show divider on top of the screen
            binding.randomRecipesDivider.visibility = View.VISIBLE
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
                // If internet connection is active then make api call to get recipes
                if (isInternetConnected) {
                    viewModel.getRandomRecipes()
                }
            }
        }
    }

    // Observer of RandomRecipesState in ViewModel
    private fun observeState(rvAdapter: RecipesAdapter, animation: SetAnimation) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.randomRecipesState.collect {
                // Start loading animation
                animation.startAnimation()
                // Give time to show animation
                delay(animation.animation.duration)
                // Check whether the recipes loaded successfully
                if (it.recipesAreLoaded) {
                    // Stop and hide animation
                    animation.finishAnimation()
                }
                // Submit the new list with recipes loaded to adapter
                rvAdapter.submitList(it.recipes)
                // Show divider on top of the screen
                binding.randomRecipesDivider.visibility = View.VISIBLE
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