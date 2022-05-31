package com.juanimozo.recipesrandomizer.domain.use_case

import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.domain.model.SimilarResults
import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow

class GetSimilarRecipesUseCase(
    private val repository: RecipesRepository
) {
    operator fun invoke(id: Int): Flow<Resource<List<SimilarResults>>> {
        // Get a list of 5 recipes that are similar to the current recipe
        return repository.getSimilarRecipes(id)
    }
}