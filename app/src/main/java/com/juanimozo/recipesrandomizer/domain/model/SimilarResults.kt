package com.juanimozo.recipesrandomizer.domain.model

data class SimilarResults(
    val id: Int,
    val image: String?,
    val readyInMinutes: Int,
    val title: String
)