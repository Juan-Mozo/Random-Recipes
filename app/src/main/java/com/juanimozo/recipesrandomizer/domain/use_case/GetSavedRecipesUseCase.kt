package com.juanimozo.recipesrandomizer.domain.use_case

import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository
import com.juanimozo.recipesrandomizer.presentation.saved_recipes.RecipeFilter
import kotlinx.coroutines.flow.Flow

class GetSavedRecipesUseCase(
    private val repository: RecipesRepository
) {
    operator fun invoke(filter: RecipeFilter): Flow<List<Recipe>> {
        return when (filter) {
            is RecipeFilter.AllRecipes -> {
                repository.getAllSavedRecipes()
            }
            is RecipeFilter.CheapRecipes -> {
                repository.getCheapSavedRecipes()
            }
            is RecipeFilter.HealthyRecipes -> {
                repository.getHealthySavedRecipes()
            }
            is RecipeFilter.QuickRecipes -> {
                repository.getQuickSavedRecipes()
            }
            is RecipeFilter.VeganRecipes -> {
                repository.getVeganSavedRecipes()
            }
        }
    }
}