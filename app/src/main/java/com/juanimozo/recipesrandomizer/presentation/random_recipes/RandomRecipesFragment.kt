package com.juanimozo.recipesrandomizer.presentation.random_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.juanimozo.recipesrandomizer.databinding.FragmentRandomRecipesBinding


class RandomRecipesFragment : Fragment() {

    private var _binding: FragmentRandomRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomRecipesBinding.inflate(inflater, container, false)

        binding.generateButton.setOnClickListener {
            val action = RandomRecipesFragmentDirections.actionRandomRecipesFragmentToRandomRecipesRVFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}