package com.juanimozo.recipesrandomizer.domain.use_case

import android.util.Log
import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow

class DeleteRecipeUseCase(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(id: Int) {
        Log.d("DeleteRecipeUseCase", "Recipe Deleted id: $id")
        return repository.deleteRecipe(id)
    }
}