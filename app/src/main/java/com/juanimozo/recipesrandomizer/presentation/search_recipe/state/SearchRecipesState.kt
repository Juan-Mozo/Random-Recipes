package com.juanimozo.recipesrandomizer.presentation.search_recipe.state

import com.juanimozo.recipesrandomizer.data.remote.dto.search_recipe.Result

data class SearchRecipesState(
    val recipes: List<Result> = emptyList(),
    val isLoading: Boolean = false,
    val areRecipesLoaded: Boolean = false
)
