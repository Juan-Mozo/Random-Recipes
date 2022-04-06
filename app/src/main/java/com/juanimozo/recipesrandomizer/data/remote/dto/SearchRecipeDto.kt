package com.juanimozo.recipesrandomizer.data.remote.dto

import com.juanimozo.recipesrandomizer.data.remote.dto.search_recipe.Result
import com.juanimozo.recipesrandomizer.domain.model.SearchResult

data class SearchRecipeDto(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
)