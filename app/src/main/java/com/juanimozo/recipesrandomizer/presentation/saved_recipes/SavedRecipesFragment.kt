package com.juanimozo.recipesrandomizer.presentation.saved_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juanimozo.recipesrandomizer.R
import com.juanimozo.recipesrandomizer.databinding.FragmentSavedRecipesBinding
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.presentation.recipe_details.RecipeDetailsFragmentDirections
import com.juanimozo.recipesrandomizer.presentation.util.RecipesAdapter
import com.juanimozo.recipesrandomizer.presentation.util.SetAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SavedRecipesFragment : Fragment() {

    private var _binding: FragmentSavedRecipesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SavedRecipesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedRecipesBinding.inflate(inflater, container, false)

        // Get saved recipes
        viewModel.getSavedRecipes()

        // Navigate to AllSavedRecipes when clicking cardView
        binding.allRecipesCardView.setOnClickListener {
            val action = SavedRecipesFragmentDirections.actionSavedRecipesFragmentToAllSavedRecipesFragment()
            findNavController().navigate(action)
        }

        // Quick recipes RecyclerView
        val rvQuickRecipes = binding.readyInRV
        val quickRecipesAdapter = SavedRecipesAdapter { recipe ->
            navigateToDetailsFragment(recipe)
        }
        concatAdapters(rvQuickRecipes, SavedRecipesHeaderAdapter(RecipeFilter.QuickRecipes()), quickRecipesAdapter)

        // Cheap recipes RecyclerView
        val rvCheapRecipes = binding.cheapRecipesRV
        val cheapRecipesAdapter = SavedRecipesAdapter { recipe ->
            navigateToDetailsFragment(recipe)
        }
        concatAdapters(rvCheapRecipes, SavedRecipesHeaderAdapter(RecipeFilter.CheapRecipes()), cheapRecipesAdapter)

        // Vegan recipes RecyclerView
        val rvVeganRecipes = binding.veganRecipesRV
        val veganRecipesAdapter = SavedRecipesAdapter { recipe ->
            navigateToDetailsFragment(recipe)
        }
        concatAdapters(rvVeganRecipes, SavedRecipesHeaderAdapter(RecipeFilter.VeganRecipes()), veganRecipesAdapter)

        // Healthy recipes RecyclerView
        val rvHealthyRecipes = binding.healthyRecipesRV
        val healthyRecipesAdapter = SavedRecipesAdapter { recipe ->
            navigateToDetailsFragment(recipe)
        }
        concatAdapters(rvHealthyRecipes, SavedRecipesHeaderAdapter(RecipeFilter.HealthyRecipes()), healthyRecipesAdapter)

        // Observe all lists in savedRecipesState
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.savedRecipesState.collect {
                // Submit new lists to adapters
                healthyRecipesAdapter.submitList(it.healthyRecipes)
                veganRecipesAdapter.submitList(it.veganRecipes)
                quickRecipesAdapter.submitList(it.quickRecipes)
                cheapRecipesAdapter.submitList(it.cheapRecipes)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    // Concat a RV header with the recipe list
    private fun concatAdapters(
        view: RecyclerView,
        headerAdapter: SavedRecipesHeaderAdapter,
        recipesAdapter: SavedRecipesAdapter
    ) {
        // Make RV horizontal
        view.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        // Set adapter with both adapters
        view.adapter = ConcatAdapter(headerAdapter, recipesAdapter)
    }

    private fun navigateToDetailsFragment(recipe: Recipe) {
        val action = SavedRecipesFragmentDirections.actionSavedRecipesFragmentToRecipeDetails(recipe)
        findNavController().navigate(action)
    }

}