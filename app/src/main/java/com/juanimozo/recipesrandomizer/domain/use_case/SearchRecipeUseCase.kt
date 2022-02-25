package com.juanimozo.recipesrandomizer.domain.use_case

import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.domain.model.SearchResult
import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRecipeUseCase(
    private val repository: RecipesRepository
) {
    operator fun invoke(
        query: String,
        cuisine: String,
        diet: String,
    ): Flow<Resource<SearchResult>> {
        if (query.isBlank()) {
            return flow {  }
        }
        return repository.searchRecipe(query, cuisine, diet)
    }
}