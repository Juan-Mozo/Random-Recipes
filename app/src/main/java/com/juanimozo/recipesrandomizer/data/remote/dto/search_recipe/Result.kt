package com.juanimozo.recipesrandomizer.data.remote.dto.search_recipe

import com.juanimozo.recipesrandomizer.domain.model.SearchResult

data class Result(
    val id: Int,
    val image: String,
    val imageType: String,
    val title: String
)