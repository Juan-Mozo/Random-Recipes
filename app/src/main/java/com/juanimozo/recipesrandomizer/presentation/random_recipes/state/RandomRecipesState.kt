package com.juanimozo.recipesrandomizer.presentation.random_recipes.state

import com.juanimozo.recipesrandomizer.domain.model.Recipe

data class RandomRecipesState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false
)