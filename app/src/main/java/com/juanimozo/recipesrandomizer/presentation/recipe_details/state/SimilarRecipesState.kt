package com.juanimozo.recipesrandomizer.presentation.recipe_details.state

import com.juanimozo.recipesrandomizer.domain.model.SimilarResults

data class SimilarRecipesState(
    val recipes: List<SimilarResults> = emptyList(),
    val isLoading: Boolean = false
)