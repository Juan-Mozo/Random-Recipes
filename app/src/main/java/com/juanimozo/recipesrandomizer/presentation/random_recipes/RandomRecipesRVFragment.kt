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
import com.juanimozo.recipesrandomizer.databinding.FragmentRandomRecipesRVBinding
import dagger.hilt.android.AndroidEntryPoint
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

        val recyclerView = binding.randomRecipesRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val recyclerViewAdapter = RandomRecipesAdapter { recipe ->

            // Navigate to RecipeDetails
            val action = RandomRecipesRVFragmentDirections.actionRandomRecipesRVFragmentToRecipeDetails(
                // ToDo:: -RVFragment- *1* / Priority: Low
                // Description: Sacar una vez integrado recipe
                title = recipe.title ?: "",
                image = recipe.image ?: "",
                instructions = recipe.instructions ?: "",
                readyIn = recipe.readyInMinutes ?: 0,
                servings = recipe.servings ?: 0,
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
                // Hide progressBar when isLoading = False
                if (!it.isLoading) {
                    binding.progressBar.visibility = View.GONE
                }
                // Submit new list to adapter
                rvAdapter.submitList(it.recipes)
            }
        }
    }
}