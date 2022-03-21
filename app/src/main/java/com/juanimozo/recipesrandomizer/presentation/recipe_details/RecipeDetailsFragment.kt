package com.juanimozo.recipesrandomizer.presentation.recipe_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanimozo.recipesrandomizer.databinding.FragmentRecipeDetailsBinding
import com.juanimozo.recipesrandomizer.presentation.util.getImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment() {

    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeDetailsViewModel by viewModels()

    private val args: RecipeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)

        // Bind recipe image
        if(args.recipe.image != null) {
            getImage(
                url = args.recipe.image!!,
                view = binding.recipeImage,
                requireContext()
            )
        }

        // Bind recipe text
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

        // RecyclerView & Adapter Ingredients
        val rvIngredients = binding.ingredientsRV
        rvIngredients.layoutManager = LinearLayoutManager(requireContext())
        val rvIngredientsAdapter = RecipeIngredientsAdapter()
        rvIngredients.adapter = rvIngredientsAdapter
        rvIngredientsAdapter.submitList(args.recipe.extendedIngredients)

        // RecyclerView & Adapter SimilarRecipes
        val rvSimilarRecipes = binding.similarRecipesRV
        rvSimilarRecipes.layoutManager = LinearLayoutManager(requireContext())
        val rvSimilarRecipesAdapter = SimilarRecipesAdapter { recipe ->
            // Get the new recipe
            viewModel.getRecipeInformation(recipe.id)
            // Load the new recipe
            observeNewRecipeState()
        }
        rvSimilarRecipes.adapter = rvSimilarRecipesAdapter

        // Get similar recipes
        viewModel.getSimilarRecipes(args.recipe.id)
        // Load similar recipes
        observeSimilarRecipesState(rvSimilarRecipesAdapter)

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

    private fun observeSimilarRecipesState(rvAdapter: SimilarRecipesAdapter) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.similarRecipesState.collect {
                // Submit new list to adapter
                rvAdapter.submitList(it.recipes)
            }
        }
    }

    private fun observeNewRecipeState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.newRecipe.collectLatest {
                // Load the new recipe selected from similar recipes
                val action = RecipeDetailsFragmentDirections.actionRecipeDetailsSelf(
                    recipe = it.recipe
                )
                findNavController().navigate(action)
            }
        }
    }

}