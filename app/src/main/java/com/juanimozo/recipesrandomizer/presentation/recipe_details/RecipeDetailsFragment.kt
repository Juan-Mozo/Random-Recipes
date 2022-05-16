package com.juanimozo.recipesrandomizer.presentation.recipe_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.juanimozo.recipesrandomizer.R
import com.juanimozo.recipesrandomizer.databinding.FragmentRecipeDetailsBinding
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.presentation.recipe_details.state.RecipeDetailsEvent
import com.juanimozo.recipesrandomizer.presentation.util.SetAnimation
import com.juanimozo.recipesrandomizer.presentation.util.getImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

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

        // Use save_animation as icon in fab
        val likeAnimationAsIcon = SetAnimation(binding.likeButtonAnimation, R.raw.save_animation)
        // Set first frame as inactive icon
        likeAnimationAsIcon.setFrame(0)

        // Bind recipe image
        if(args.recipe.image != null && args.recipe.image!!.isNotEmpty()) {
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
        binding.pairedWineText.text = args.recipe.pairedWineText

        // Floating Action Button
        binding.likeRecipeButton.setOnClickListener {
            // If recipe is already in favourites, delete it
            if (viewModel.isRecipeFavorite.value) {
                manageLikeButton(true, args.recipe, likeAnimationAsIcon)
            } else {
                manageLikeButton(false, args.recipe, likeAnimationAsIcon)
            }
        }


        // Set transparency icons
            // Cheap Icon
        setTransparency(args.recipe.cheap, binding.cheapIcon, binding.cheapTextBoolean)
            // Vegan Icon
        setTransparency(args.recipe.vegan, binding.veganIcon, binding.veganTextBoolean)
            // Vegetarian Icon
        setTransparency(args.recipe.vegetarian, binding.vegetarianIcon, binding.vegetarianTextBoolean)
            // Gluten Free Icon
        setTransparency(args.recipe.glutenFree, binding.glutenFreeIcon, binding.glutenFreeTextBoolean)

        // RecyclerView & Adapter Ingredients
        val rvIngredients = binding.ingredientsRV
        rvIngredients.layoutManager = LinearLayoutManager(requireContext())
        val rvIngredientsAdapter = RecipeIngredientsAdapter()
        rvIngredients.adapter = rvIngredientsAdapter
        rvIngredientsAdapter.submitList(args.recipe.extendedIngredients)

        // RecyclerView & Adapter SimilarRecipes
        val rvSimilarRecipes = binding.similarRecipesRV
        rvSimilarRecipes.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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

    private fun setTransparency(
        value: Boolean,
        // Icon
        iV: ImageView,
        // TextView with Yes or No
        tV: TextView
    ) {
        if (!value) {
            iV.imageAlpha = 30
            tV.text = getString(R.string.no)
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
            viewModel.newRecipe.collect {
                // Load the new recipe selected from similar recipes
                val action = RecipeDetailsFragmentDirections.actionRecipeDetailsSelf(
                    recipe = it
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun manageLikeButton(isFavorite: Boolean, recipe: Recipe, view: SetAnimation) {
        // If recipe is not already liked, save it and change the icon of FAB
        if (!isFavorite) {
            viewModel.onLikedRecipe(RecipeDetailsEvent.MakeFavourite(recipe))
            view.setFrame(39)
        } else {
            // If recipes is already saved, delete it and change the icon of FAB
            viewModel.onLikedRecipe(RecipeDetailsEvent.UndoFavourite(recipe))
            view.setFrame(0)
        }
    }

}