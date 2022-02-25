package com.juanimozo.recipesrandomizer.domain.use_case

import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.domain.model.RandomRecipe
import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow

class GetRandomRecipesUseCase(
    private val repository: RecipesRepository
) {
    operator fun invoke(): Flow<Resource<List<RandomRecipe>>> {
        return repository.getRandomRecipes()
    }
}