package com.juanimozo.recipesrandomizer.data.remote.dto.random_recipes

data class Step(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>,
    val length: Length,
    val number: Int,
    val step: String
)