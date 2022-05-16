package com.juanimozo.recipesrandomizer.domain.repository

import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.data.local.RecipeEntity
import com.juanimozo.recipesrandomizer.data.remote.dto.search_recipe.Result
import com.juanimozo.recipesrandomizer.domain.model.*
import com.juanimozo.recipesrandomizer.presentation.saved_recipes.RecipeFilter
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    fun getRecipeInfo(id: Int): Flow<Resource<Recipe>>

    fun getRandomRecipes(): Flow<Resource<List<Recipe>>>

    fun searchRecipe(query: String, cuisine: String, diet: String): Flow<Resource<List<Result>>>

    fun getSimilarRecipes(id: Int): Flow<Resource<List<SimilarResults>>>

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteRecipe(id: Int)

    fun getAllSavedRecipes(): Flow<List<Recipe>>

    fun getQuickSavedRecipes(): Flow<List<Recipe>>

    fun getVeganSavedRecipes(): Flow<List<Recipe>>

    fun getCheapSavedRecipes(): Flow<List<Recipe>>

    fun getHealthySavedRecipes(): Flow<List<Recipe>>

}