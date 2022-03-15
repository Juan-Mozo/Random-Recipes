package com.juanimozo.recipesrandomizer.presentation.recipe_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanimozo.recipesrandomizer.databinding.FragmentRecipeDetailsBinding
import com.juanimozo.recipesrandomizer.presentation.util.getImage

class RecipeDetails : Fragment() {

    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: RecipeDetailsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)

        // Bind Image
        if(args.recipe.image != null) {
            getImage(
                url = args.recipe.image!!,
                view = binding.recipeImage,
                requireContext()
            )
        }

        // Bind Text
        binding.titleTextView.text = args.recipe.title
        binding.servingsAmount.text = args.recipe.servings.toString()
        binding.readyInAmount.text = args.recipe.readyInMinutes.toString()
        binding.instructionsText.text = args.recipe.instructions

        // Set transparency icons
            // Cheap Icon
        setTransparency(args.recipe.cheap, binding.cheapIcon)
            // Vegan Icon
        setTransparency(args.recipe.vegan, binding.veganIcon)
            // Vegetarian Icon
        setTransparency(args.recipe.vegetarian, binding.vegetarianIcon)
            // Gluten Free Icon
        setTransparency(args.recipe.glutenFree, binding.glutenFreeIcon)

        // RecyclerView & Adapter
        val recyclerView = binding.ingredientsRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val recyclerViewAdapter = RecipeIngredientsAdapter()
        recyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.submitList(args.recipe.extendedIngredients)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setTransparency(
        value: Boolean,
        iV: ImageView
    ) {
        if (!value) {
            iV.imageAlpha = 30
        }
    }

}