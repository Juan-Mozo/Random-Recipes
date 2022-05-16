package com.juanimozo.recipesrandomizer.presentation.saved_recipes.all_saved_recipes

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
import com.juanimozo.recipesrandomizer.databinding.FragmentAllSavedRecipesBinding
import com.juanimozo.recipesrandomizer.presentation.util.RecipesAdapter
import com.juanimozo.recipesrandomizer.presentation.util.SetAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AllSavedRecipesFragment : Fragment() {

    private var _binding: FragmentAllSavedRecipesBinding? = null
    private val binding get() = _binding!!

    val viewModel: AllSavedRecipesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllSavedRecipesBinding.inflate(inflater, container, false)

        val loadingAnimation = SetAnimation(binding.loadingSavedRecipesAnimation, R.raw.loading_saved_recipes_animation)

        val recyclerView = binding.allSavedRecipesRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val recyclerViewAdapter = RecipesAdapter { recipe ->

            // Navigate to RecipeDetailsFragment
            val action = AllSavedRecipesFragmentDirections.actionAllSavedRecipesFragmentToRecipeDetails(
                recipe = recipe
            )
            findNavController().navigate(action)
        }
        recyclerView.adapter = recyclerViewAdapter

        // Only start animation when there are no recipes loaded in viewModel
        if (!viewModel.allRecipesState.value.recipesAreLoaded) {
            // Observe RandomRecipesState
            observeState(recyclerViewAdapter, loadingAnimation)
        } else {
            recyclerViewAdapter.submitList(viewModel.allRecipesState.value.recipes)
            binding.loadingSavedRecipesAnimation.visibility = View.GONE
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeState(rvAdapter: RecipesAdapter, animation: SetAnimation) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allRecipesState.collect {
                // Start loading animation
                animation.startAnimation()
                delay(1500)
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