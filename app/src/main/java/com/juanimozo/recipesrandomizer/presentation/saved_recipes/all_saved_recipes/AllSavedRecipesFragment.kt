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

        // Set RecyclerView
        val recyclerView = binding.allSavedRecipesRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Set Adapter
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
            observeState(recyclerViewAdapter)
        } else {
            // Submit current list to adapter
            recyclerViewAdapter.submitList(viewModel.allRecipesState.value.recipes)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Observe SavedRecipes state
    private fun observeState(rvAdapter: RecipesAdapter) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allRecipesState.collect {
                // Submit new list to adapter
                rvAdapter.submitList(it.recipes)
            }
        }
    }

}