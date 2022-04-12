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

        // Start loading animation
        SetAnimation().startAnimation(binding.loadingFoodAnimation, R.raw.loading_food)

        val recyclerView = binding.randomRecipesRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val recyclerViewAdapter = RandomRecipesAdapter { recipe ->

            // Navigate to RecipeDetailsFragment
            val action = RandomRecipesRVFragmentDirections.actionRandomRecipesRVFragmentToRecipeDetails(
                recipe = recipe
            )
            findNavController().navigate(action)
        }
        recyclerView.adapter = recyclerViewAdapter

        // Observe RandomRecipesState
        observeState(recyclerViewAdapter)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Observer of RandomRecipesState in ViewModel
    private fun observeState(rvAdapter: RandomRecipesAdapter) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.randomRecipesState.collect {
                delay(2000)
                // Stop and hide animation when isLoading = False
                if (!it.isLoading) {
                    SetAnimation().finishAnimation(binding.loadingFoodAnimation)
                }
                // Submit new list to adapter
                rvAdapter.submitList(it.recipes)
            }
        }
    }
}