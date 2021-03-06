package com.juanimozo.recipesrandomizer.domain.use_case

import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow

class GetRecipeInfoUseCase(
    private val repository: RecipesRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Recipe>> {
        // Get complete information of recipe by id
        return repository.getRecipeInfo(id)
    }
}