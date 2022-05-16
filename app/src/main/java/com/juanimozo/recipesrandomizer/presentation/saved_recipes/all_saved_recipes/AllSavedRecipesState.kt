package com.juanimozo.recipesrandomizer.presentation.saved_recipes.all_saved_recipes

import com.juanimozo.recipesrandomizer.domain.model.Recipe

data class AllSavedRecipesState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val recipesAreLoaded: Boolean = false
)