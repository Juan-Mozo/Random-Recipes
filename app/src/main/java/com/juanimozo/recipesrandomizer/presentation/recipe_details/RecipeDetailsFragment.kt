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

        // Create an animation for the FAB
        val likeAnimation = SetAnimation(binding.likeButtonAnimation, R.raw.save_animation)
        // Create an animation for the FAB to use as an icon
        val likeAnimationAsIcon = SetAnimation(binding.likeButtonIcon, R.raw.save_animation)

        // Check if current recipe is already saved as favorite
        viewModel.checkLikedRecipe(args.recipe.id)
        observeIsRecipeFavorite(likeAnimationAsIcon)

        // Bind recipe image only when it isn't null and there's internet connection
        if (args.recipe.image != null && args.recipe.image!!.isNotEmpty() && viewModel.internetConnection.value
        ) {
            // Get the recipe image with picasso
            getImage(url = args.recipe.image!!, view = binding.recipeImage, requireContext())
        }

        // Bind recipe texts
        binding.titleTextView.text = args.recipe.title
        binding.servingsAmount.text = args.recipe.servings.toString()
        binding.readyInAmount.text = args.recipe.readyInMinutes.toString()
        binding.instructionsText.text = args.recipe.instructions

        // Bind paired wine section
        val pairedWine = args.recipe.pairedWineText
        // Check if paired wine is null
        if (pairedWine.isNullOrBlank()) {
            // Hide the entire section
            binding.pairedWineText.visibility = View.INVISIBLE
            binding.pairedWineTitle.visibility = View.INVISIBLE
        } else {
            // Bind text
            binding.pairedWineText.text = pairedWine
        }

        // Floating Action Button
        binding.likeRecipeButton.setOnClickListener {
            // Check if recipe is already in favourites
            if (viewModel.isRecipeFavorite.value) {
                // Delete recipe in ROOM db  and change the icon of FAB
                manageLikeButton(true, args.recipe, likeAnimation, likeAnimationAsIcon.animation)
            } else {
                // Save recipe in ROOM db and start animation in FAB
                manageLikeButton(false, args.recipe, likeAnimation, likeAnimationAsIcon.animation)
            }
        }

        /* For each information card in the recipe:
            - Check boolean value of given condition (ex: whether a recipe is vegan or not)
            - If value is true then show opaque icon and a "Yes" text
            - If value is false then show translucent icon and a "No" text
         */
            // Cheap information Card
        setTransparency(args.recipe.cheap, binding.cheapIcon, binding.cheapTextBoolean)
            // Vegan information Card
        setTransparency(args.recipe.vegan, binding.veganIcon, binding.veganTextBoolean)
            // Vegetarian information Card
        setTransparency(args.recipe.vegetarian, binding.vegetarianIcon, binding.vegetarianTextBoolean)
            // Gluten Free information Card
        setTransparency(args.recipe.glutenFree, binding.glutenFreeIcon, binding.glutenFreeTextBoolean)

        // Set RecyclerView & Adapter for Ingredients
        val rvIngredients = binding.ingredientsRV
        rvIngredients.layoutManager = LinearLayoutManager(requireContext())
        val rvIngredientsAdapter = RecipeIngredientsAdapter()
        rvIngredients.adapter = rvIngredientsAdapter
        // Load list of ingredients
        rvIngredientsAdapter.submitList(args.recipe.extendedIngredients)

        // Set RecyclerView & Adapter for SimilarRecipes
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

    // Modify recipe's information cards
    private fun setTransparency(value: Boolean, icon: ImageView, tV: TextView) {
        // If the recipe doesn't accomplish the card requirements, else leave it as it was
        if (!value) {
            // Make card's icon translucent
            icon.imageAlpha = 30
            // Change text to No
            tV.text = getString(R.string.no)
        }
    }

    // Observer of similarRecipesState in ViewModel
    private fun observeSimilarRecipesState(rvAdapter: SimilarRecipesAdapter) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.similarRecipesState.collect {
                // Upgrade SimilarRecipesAdapter when new recipes are loaded
                rvAdapter.submitList(it.recipes)
            }
        }
    }

    // Observer of newRecipeState in ViewModel
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

    // Observer of isRecipeFavorite State in ViewModel
    private fun observeIsRecipeFavorite(view: SetAnimation) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isRecipeFavorite.collect { isFavorite ->
                // Use animation's frames as icons on FAB
                if (isFavorite) {
                    // Show a solid bookmark icon
                    view.setFrame(39)
                } else {
                    // Show a linear bookmark icon
                    view.setFrame(0)
                }
            }
        }
    }

    // Observer of InternetConnection State in ViewModel
    private fun observeInternetConnection() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.internetConnection.collect { isInternetConnected ->
                if (isInternetConnected) {
                    // If internet connection is active then make api call to get similar recipes
                    viewModel.getSimilarRecipes(args.recipe.id)
                }
            }
        }
    }

    // Save or delete recipe from database
    private fun manageLikeButton(isFavorite: Boolean, recipe: Recipe, view: SetAnimation, icon: LottieAnimationView) {
        // If recipe is not already liked, save it and start animation in FAB
        if (!isFavorite) {
            val animationJob = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + animationJob)

            // Save recipe in ROOM db and upgrade state
            viewModel.onLikedRecipe(RecipeDetailsEvent.MakeFavourite(recipe))
            // Launch like animation in the main thread
            uiScope.launch(Dispatchers.Main) {
                view.likeAnimation(icon, 39)
                animationJob.cancel()
            }
        } else {
            // If recipes is already saved, delete it and change the icon of FAB
            viewModel.onLikedRecipe(RecipeDetailsEvent.UndoFavourite(recipe))
        }
    }

    // Check status of internet connection and upgrade InternetConnection State in ViewModel
    private fun checkInternetConnection() {
        val isInternetConnected = InternetConnection(requireContext()).checkInternetConnection()
        if (isInternetConnected) {
            // Set internet connection status to true
            viewModel.handleInternetConnection(isInternetConnected = true)
        } else {
            // Set internet connection status to false
            viewModel.handleInternetConnection(isInternetConnected = false)
        }
    }

}