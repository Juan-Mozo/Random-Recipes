package com.juanimozo.recipesrandomizer.data.remote.dto.random_recipes

data class Ingredient(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String
)