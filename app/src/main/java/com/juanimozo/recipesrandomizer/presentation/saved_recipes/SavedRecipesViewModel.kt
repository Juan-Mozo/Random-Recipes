package com.juanimozo.recipesrandomizer.presentation.saved_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.domain.use_case.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedRecipesViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {

    private val _savedRecipesState = MutableStateFlow(SavedRecipesState(emptyList(), emptyList(), emptyList(), emptyList()))
    val savedRecipesState: StateFlow<SavedRecipesState> = _savedRecipesState

    private var getSavedRecipesJob: Job? = null

    fun getSavedRecipes() {
        getSavedRecipesJob?.cancel()
        getSavedRecipesJob = viewModelScope.launch {
            // Get saved quick recipes
            recipeUseCases.getSavedRecipesUseCase(RecipeFilter.QuickRecipes()).collect { result ->
                manageResult(result, RecipeFilter.QuickRecipes())
            }
            // Get saved cheap recipes
            recipeUseCases.getSavedRecipesUseCase(RecipeFilter.CheapRecipes()).collect { result ->
                manageResult(result, RecipeFilter.CheapRecipes())
            }
            // Get saved vegan recipes
            recipeUseCases.getSavedRecipesUseCase(RecipeFilter.VeganRecipes()).collect { result ->
                manageResult(result, RecipeFilter.VeganRecipes())
            }
            // Get saved healthy recipes
            recipeUseCases.getSavedRecipesUseCase(RecipeFilter.HealthyRecipes()).collect { result ->
                manageResult(result, RecipeFilter.HealthyRecipes())
            }
        }
    }

    private fun manageResult(result: List<Recipe>, filter: RecipeFilter) {
        // Update result by filter
        when (filter) {
            is RecipeFilter.CheapRecipes -> {
                _savedRecipesState.value = savedRecipesState.value.copy(
                    cheapRecipes = result)
            }
            is RecipeFilter.HealthyRecipes -> {
                _savedRecipesState.value = savedRecipesState.value.copy(
                            healthyRecipes = result)
            }
            is RecipeFilter.QuickRecipes -> {
                _savedRecipesState.value = savedRecipesState.value.copy(
                    quickRecipes = result)
            }
            is RecipeFilter.VeganRecipes -> {
                _savedRecipesState.value = savedRecipesState.value.copy(
                    veganRecipes = result)
            }
        }
    }
}