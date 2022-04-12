package com.juanimozo.recipesrandomizer.data.remote.dto

import com.juanimozo.recipesrandomizer.data.remote.dto.random_recipe.Recipe

data class RandomRecipesDto(
    val recipes: List<Recipe>
)