package com.juanimozo.recipesrandomizer.presentation.recipe_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.domain.use_case.RecipeUseCases
import com.juanimozo.recipesrandomizer.presentation.recipe_details.state.RecipeDetailsEvent
import com.juanimozo.recipesrandomizer.presentation.recipe_details.state.SimilarRecipesState
import com.juanimozo.recipesrandomizer.presentation.util.EmptyModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RandomRecipesRV ViewModel"

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {

    private val _similarRecipesState = MutableStateFlow(SimilarRecipesState(emptyList(), false))
    val similarRecipesState: StateFlow<SimilarRecipesState> = _similarRecipesState

    private val _newRecipe = MutableSharedFlow<Recipe>()
    val newRecipe = _newRecipe.asSharedFlow()

    private val _isRecipeFavorite = MutableStateFlow(false)
    val isRecipeFavorite = _isRecipeFavorite.asStateFlow()

    private val _internetConnection = MutableStateFlow(false)
    val internetConnection: StateFlow<Boolean> = _internetConnection

    private var checkLikedRecipeJob: Job? = null
    private var getSimilarRecipesJob: Job? = null
    private var getRecipeInfoJob: Job? = null
    private var likeRecipeJob: Job? = null

    // Check if the current recipe is already saved in the ROOM database
    fun checkLikedRecipe(id: Int) {
        checkLikedRecipeJob?.cancel()
        checkLikedRecipeJob = viewModelScope.launch {
            recipeUseCases.checkIfRecipesIsSavedUseCase(id)
                .collect { result ->
                    _isRecipeFavorite.value = result
                }
        }
    }

    // Get a list of 5 recipes that are similar to the current recipe
    fun getSimilarRecipes(id: Int) {
        getSimilarRecipesJob?.cancel()
        getSimilarRecipesJob = viewModelScope.launch {
            recipeUseCases.getSimilarRecipesUseCase(id)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _similarRecipesState.value = similarRecipesState.value.copy(
                                recipes = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _similarRecipesState.value = similarRecipesState.value.copy(
                                recipes = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                        is Resource.Error -> {
                            _similarRecipesState.value = similarRecipesState.value.copy(
                                recipes = result.data ?: emptyList(),
                                isLoading = false
                            )
                            Log.e(TAG, "An error occurred while getting similar recipes. Result: ${result.data}")
                        }
                    }

                }
        }
    }

    // Load a new recipe
    fun getRecipeInformation(id: Int) {
        getRecipeInfoJob?.cancel()
        getRecipeInfoJob = viewModelScope.launch {
            recipeUseCases.getRecipeInfoUseCase(id)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _newRecipe.emit(result.data ?: EmptyModel.emptyRecipe)
                        }
                        is Resource.Loading -> {}
                        is Resource.Error -> {
                            Log.e(TAG, "An error occurred while getting recipe information. Result: ${result.data}")
                        }
                    }

                }
        }
    }

    // Handle user interaction with like button
    fun onLikedRecipe(
        event: RecipeDetailsEvent
    ) {
        when (event) {
            // Save recipe in ROOM database
            is RecipeDetailsEvent.MakeFavourite -> {
                likeRecipeJob?.cancel()
                likeRecipeJob = viewModelScope.launch {
                    recipeUseCases.saveRecipeUseCase(event.recipe)
                    _isRecipeFavorite.value = true
                }
            }
            // Delete recipe from ROOM database
            is RecipeDetailsEvent.UndoFavourite -> {
                likeRecipeJob?.cancel()
                likeRecipeJob = viewModelScope.launch {
                    recipeUseCases.deleteRecipeUseCase(event.recipe.id)
                    _isRecipeFavorite.value = false
                }
            }
        }
    }

    // Update internet connection state
    fun handleInternetConnection(isInternetConnected: Boolean) {
        _internetConnection.value = isInternetConnected
    }

}