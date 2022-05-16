package com.juanimozo.recipesrandomizer.domain.use_case

import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository

class SaveRecipeUseCase(
    private val repository: RecipesRepository
) {
   suspend operator fun invoke(recipe: Recipe) {
       return repository.saveRecipe(recipe)
   }
}