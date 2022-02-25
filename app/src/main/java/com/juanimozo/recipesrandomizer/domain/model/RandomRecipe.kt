package com.juanimozo.recipesrandomizer.domain.model

data class RandomRecipe(
    val id: Int,
    val title: String,
    val image: String,
    val instructions: String,
    val servings: Int,
    val readyInMinutes: Int,
    val cuisines: List<Any>,
    val dishType: List<String>,
    val cheap: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val glutenFree: Boolean,
    val healthScore: Double
)
