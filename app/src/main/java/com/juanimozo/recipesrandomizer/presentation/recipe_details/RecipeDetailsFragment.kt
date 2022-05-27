package com.juanimozo.recipesrandomizer.presentation.recipe_details

import android.os.Bundle
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
import com.google.android.material.snackbar.Snackbar
import com.juanimozo.recipesrandomizer.R
import com.juanimozo.recipesrandomizer.databinding.FragmentRecipeDetailsBinding
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.presentation.recipe_details.state.RecipeDetailsEvent
import com.juanimozo.recipesrandomizer.presentation.util.InternetConnection
import com.juanimozo.recipesrandomizer.presentation.util.SetAnimation
import com.juanimozo.recipesrandomizer.presentation.util.getImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
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

        // Check internet connection
        checkInternetConnection()
        observeInternetConnection()

        // Use save_animation as icon in fab
        val likeAnimation = SetAnimation(binding.likeButtonAnimation, R.raw.save_animation)
        val likeAnimationAsIcon = SetAnimation(binding.likeButtonIcon, R.raw.save_animation)

        // Check if current recipe is already saved as favorite
        viewModel.checkLikedRecipe(args.recipe.id)
        observeIsRecipeFavorite(likeAnimationAsIcon)

        // Bind recipe image
        if (args.recipe.image != null
            && args.recipe.image!!.isNotEmpty()
            && viewModel.internetConnection.value
        ) {
            // If the url is valid and there's internet connection get the recipe image with picasso
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
        val pairedWine = args.recipe.pairedWineText
        if (pairedWine.isNullOrBlank()) {
            // When there's not paired wines, hide that section
            binding.pairedWineText.visibility = View.INVISIBLE
            binding.pairedWineTitle.visibility = View.INVISIBLE
        } else {
            binding.pairedWineText.text = pairedWine
        }

        // Floating Action Button
        binding.likeRecipeButton.setOnClickListener {
            // If recipe is already in favourites, delete it
            if (viewModel.isRecipeFavorite.value) {
                manageLikeButton(true, args.recipe, likeAnimation, likeAnimationAsIcon.animation)
            } else {
                manageLikeButton(false, args.recipe, likeAnimation, likeAnimationAsIcon.animation)
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
            // If there´s internet connection get the new recipe
            checkInternetConnection()
            if (viewModel.internetConnection.value) {
                // Get the new recipe
                viewModel.getRecipeInformation(recipe.id)
            } else {
                // If internet connections is unavailable inform the user
                Snackbar.make(requireView(), R.string.no_internet_connection, Snackbar.LENGTH_SHORT).show()
            }
            // Load the new recipe
            observeNewRecipeState()
        }
        rvSimilarRecipes.adapter = rvSimilarRecipesAdapter

        // Load similar recipes
        observeSimilarRecipesState(rvSimilarRecipesAdapter)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setTransparency(value: Boolean, icon: ImageView, tV: TextView) {
        if (!value) {
            icon.imageAlpha = 30
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

    private fun observeIsRecipeFavorite(view: SetAnimation) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isRecipeFavorite.collect { isFavorite ->
                if (isFavorite) {
                    view.setFrame(39)
                } else {
                    view.setFrame(0)
                }
            }
        }
    }

    private fun observeInternetConnection() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.internetConnection.collect { isInternetConnected ->
                if (isInternetConnected) {
                    // Get similar recipes
                    viewModel.getSimilarRecipes(args.recipe.id)
                }
            }
        }
    }

    private fun manageLikeButton(isFavorite: Boolean, recipe: Recipe, view: SetAnimation, icon: LottieAnimationView) {
        // If recipe is not already liked, save it and start animation in FAB
        if (!isFavorite) {
            val animationJob = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + animationJob)

            viewModel.onLikedRecipe(RecipeDetailsEvent.MakeFavourite(recipe))

            uiScope.launch(Dispatchers.Main) {
                view.likeAnimation(icon, 39)
                animationJob.cancel()
            }

        } else {
            // If recipes is already saved, delete it and change the icon of FAB
            viewModel.onLikedRecipe(RecipeDetailsEvent.UndoFavourite(recipe))
        }
    }

    private fun checkInternetConnection() {
        val isInternetConnected = InternetConnection(requireContext()).checkInternetConnection()
        if (isInternetConnected) {
            viewModel.handleInternetConnection(isInternetConnected = true)
        } else {
            viewModel.handleInternetConnection(isInternetConnected = false)
        }
    }

}