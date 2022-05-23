package com.juanimozo.recipesrandomizer.presentation.search_recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.juanimozo.recipesrandomizer.R
import com.juanimozo.recipesrandomizer.databinding.FragmentSearchRecipesBinding
import com.juanimozo.recipesrandomizer.presentation.util.InternetConnection
import com.juanimozo.recipesrandomizer.presentation.util.SpinnerType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRecipesFragment : Fragment() {

    private var _binding: FragmentSearchRecipesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchRecipesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchRecipesBinding.inflate(inflater, container, false)

        // Cuisine Spinner
        SpinnerAdapter().setAdapter(requireContext(), binding.cuisineSpinner, R.array.cuisine_array, binding.cuisineSpinner, viewModel, SpinnerType.Cuisine())
        // Diet Spinner
        SpinnerAdapter().setAdapter(requireContext(), binding.dietSpinner, R.array.diet_array, binding.dietSpinner, viewModel, SpinnerType.Diet())

        binding.searchButton.setOnClickListener {

            // Check internet connection
            val internetConnection = InternetConnection(requireContext()).checkInternetConnection()
            if (internetConnection) {
                // If internet connection is available check user input then direct to Recyclerview
                // Query written by user
                val query = binding.searchEditText.text
                // Filters selected by user
                val cuisine = viewModel.searchState.value.cuisine
                val diet = viewModel.searchState.value.diet
                // Check user input
                if (query.isBlank()) {
                    // Inform the user that text is blank
                    Snackbar.make(requireView(), R.string.text_is_blank, Snackbar.LENGTH_SHORT).show()
                } else {
                    // Direct to Recyclerview
                    val action = SearchRecipesFragmentDirections.actionSearchRecipesFragmentToSearchRecipesRVFragment(query.toString(), cuisine, diet)
                    findNavController().navigate(action)
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}