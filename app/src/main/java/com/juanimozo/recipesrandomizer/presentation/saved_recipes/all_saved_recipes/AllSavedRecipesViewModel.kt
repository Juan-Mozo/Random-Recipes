package com.juanimozo.recipesrandomizer.presentation.saved_recipes.all_saved_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanimozo.recipesrandomizer.domain.use_case.RecipeUseCases
import com.juanimozo.recipesrandomizer.presentation.saved_recipes.RecipeFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllSavedRecipesViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {

    private val _allRecipesState = MutableStateFlow(AllSavedRecipesState(emptyList(), false))
    val allRecipesState: StateFlow<AllSavedRecipesState> = _allRecipesState

    private var getSavedRecipesJob: Job? = null

    init {
        getSavedRecipes()
    }

    // Get all saved recipes
    private fun getSavedRecipes() {
        getSavedRecipesJob?.cancel()
        getSavedRecipesJob = viewModelScope.launch {
            recipeUseCases.getSavedRecipesUseCase(RecipeFilter.AllRecipes())
                .collect { result ->
                    _allRecipesState.value = allRecipesState.value.copy(
                        recipes = result,
                        isLoading = false,
                        recipesAreLoaded = true
                    )
                }
        }
    }
}