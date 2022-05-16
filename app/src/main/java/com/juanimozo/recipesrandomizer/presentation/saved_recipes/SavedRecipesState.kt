package com.juanimozo.recipesrandomizer.presentation.saved_recipes

import com.juanimozo.recipesrandomizer.domain.model.Recipe

data class SavedRecipesState(
    val quickRecipes: List<Recipe>,
    val cheapRecipes: List<Recipe>,
    val veganRecipes: List<Recipe>,
    val healthyRecipes: List<Recipe>,
)
