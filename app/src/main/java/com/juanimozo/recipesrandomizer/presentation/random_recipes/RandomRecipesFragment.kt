package com.juanimozo.recipesrandomizer.presentation.random_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.juanimozo.recipesrandomizer.R
import com.juanimozo.recipesrandomizer.databinding.FragmentRandomRecipesBinding
import com.juanimozo.recipesrandomizer.presentation.util.InternetConnection

class RandomRecipesFragment : Fragment() {

    private var _binding: FragmentRandomRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomRecipesBinding.inflate(inflater, container, false)

        binding.generateButton.setOnClickListener {
            // Check internet connection
            val internetConnection = InternetConnection(requireContext()).checkInternetConnection()
            if (internetConnection) {
                // If internet connection is available direct to Recyclerview
                val action = RandomRecipesFragmentDirections.actionRandomRecipesFragmentToRandomRecipesRVFragment()
                findNavController().navigate(action)
            } else {
                // If internet connections is unavailable inform the user
                Snackbar.make(requireView(), R.string.no_internet_connection, Snackbar.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}