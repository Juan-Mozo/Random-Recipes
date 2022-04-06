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
import com.juanimozo.recipesrandomizer.databinding.FragmentSearchRecipesRvBinding
import com.juanimozo.recipesrandomizer.presentation.search_recipe.SearchRecipesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchRecipesRVFragment : Fragment() {

    private var _binding: FragmentSearchRecipesRvBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchRecipeRVViewModel by viewModels()

    private val args: SearchRecipesRVFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchRecipesRvBinding.inflate(inflater, container, false)

        // Make call to bring list of results
        viewModel.searchRecipes(
            query = args.query,
            cuisine = args.cuisine,
            diet = args.diet
        )

        val recyclerView = binding.searchRecipesRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val recyclerViewAdapter = SearchRecipesAdapter {

            viewModel.getRecipeInformation(it.id)
            observeNewRecipeState()

        }
        recyclerView.adapter = recyclerViewAdapter

        observeState(recyclerViewAdapter)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Observer of RandomRecipesState in ViewModel
    private fun observeState(rvAdapter: SearchRecipesAdapter) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchRecipe.collect {
                // Hide progressBar when isLoading = False
                if (!it.isLoading) {
                    binding.progressBar.visibility = View.GONE
                }
                // Submit new list to adapter
                rvAdapter.submitList(it.recipes)
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

}