package com.juanimozo.recipesrandomizer.domain.use_case

import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow

class CheckIfRecipesIsSavedUseCase(
    private val repository: RecipesRepository
) {
    operator fun invoke(id: Int): Flow<Boolean> {
        return repository.checkIfRecipeIsSaved(id)
    }
}