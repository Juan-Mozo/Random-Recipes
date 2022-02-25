package com.juanimozo.recipesrandomizer.data.remote.dto

import com.juanimozo.recipesrandomizer.data.remote.dto.random_recipes.Recipe

data class RandomRecipesDto(
    val recipes: List<Recipe>
)