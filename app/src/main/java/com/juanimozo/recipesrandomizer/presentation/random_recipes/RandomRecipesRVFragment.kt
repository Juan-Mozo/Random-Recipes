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
import com.juanimozo.recipesrandomizer.presentation.util.RecipesAdapter
import com.juanimozo.recipesrandomizer.presentation.util.SetAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RandomRecipesRVFragment : Fragment() {

    private var _binding: FragmentRandomRecipesRVBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RandomRecipesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomRecipesRVBinding.inflate(inflater, container, false)

        val loadingAnimation = SetAnimation(binding.loadingFoodAnimation, R.raw.loading_food)

        val recyclerView = binding.randomRecipesRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val recyclerViewAdapter = RecipesAdapter { recipe ->

            // Navigate to RecipeDetailsFragment
            val action = RandomRecipesRVFragmentDirections.actionRandomRecipesRVFragmentToRecipeDetails(
                recipe = recipe
            )
            findNavController().navigate(action)
        }
        recyclerView.adapter = recyclerViewAdapter

        // Only start animation when there are no recipes loaded in viewModel
        if(!viewModel.randomRecipesState.value.recipesAreLoaded) {
            // Observe RandomRecipesState
            observeState(recyclerViewAdapter, loadingAnimation)
        } else {
            recyclerViewAdapter.submitList(viewModel.randomRecipesState.value.recipes)
            binding.loadingFoodAnimation.visibility = View.GONE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Observer of RandomRecipesState in ViewModel
    private fun observeState(rvAdapter: RecipesAdapter, animation: SetAnimation) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.randomRecipesState.collect {
                // Start loading animation
                animation.startAnimation()
                delay(animation.animation.duration)
                // Stop and hide animation when isLoading = False
                if (it.recipesAreLoaded) {
                    animation.finishAnimation()
                }
                // Submit new list to adapter
                rvAdapter.submitList(it.recipes)
            }
        }
    }
}