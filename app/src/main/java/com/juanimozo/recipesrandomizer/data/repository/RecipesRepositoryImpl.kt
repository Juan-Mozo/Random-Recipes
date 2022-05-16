package com.juanimozo.recipesrandomizer.data.repository

import android.util.Log
import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.data.local.RecipeDao
import com.juanimozo.recipesrandomizer.data.local.RecipeEntity
import com.juanimozo.recipesrandomizer.data.remote.SpoonacularApi
import com.juanimozo.recipesrandomizer.data.remote.dto.search_recipe.Result
import com.juanimozo.recipesrandomizer.domain.model.*
import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository
import com.juanimozo.recipesrandomizer.presentation.saved_recipes.RecipeFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val api: SpoonacularApi,
    private val db: RecipeDao
): RecipesRepository {

    // API
    override fun getRecipeInfo(id: Int): Flow<Resource<Recipe>> = flow {
        emit(Resource.Loading())

        try {
            val recipeInfo = api.getRecipeInformation(id).toRecipeEntity()
            emit(Resource.Success(data = recipeInfo))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Something went wrong"))
        }
    }

    override fun getRandomRecipes(): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading())

        try {
            val randomRecipes = api.getRandomRecipes().recipes.map { it.toRecipe() }
            emit(Resource.Success(data = randomRecipes))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Something went wrong"))
        }
    }

    override fun searchRecipe(
        query: String,
        cuisine: String,
        diet: String
    ): Flow<Resource<List<Result>>> = flow {
        emit(Resource.Loading())

        try {
            val result = api.searchRecipe(
                query = query,
                cuisine = cuisine,
                diet = diet
            ).results
            emit(Resource.Success(data = result))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Something went wrong"))
        }

    }

    override fun getSimilarRecipes(id: Int): Flow<Resource<List<SimilarResults>>> = flow {
        emit(Resource.Loading())

        try {
            val similarRecipes = api.getSimilarRecipes(id).map { it.toRecipeEntity() }
            emit(Resource.Success(data = similarRecipes))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Something went wrong"))
        }
    }

    // ROOM

    override suspend fun saveRecipe(recipe: Recipe) {
        db.insertRecipe(recipe.toRecipeEntity())
    }

    override suspend fun deleteRecipe(id: Int) {
        db.deleteRecipe(id)
    }

    override fun getAllSavedRecipes(): Flow<List<Recipe>> = flow {
        try {
            val result = db.selectAllRecipes().map { it.toRecipeModel() }
            if(result.isNullOrEmpty()) {
                Log.d("RepoImpl", "EMPTY")
            } else {
                Log.d("RepoImpl", "getAllSavedRecipes: ${result[0].title}")
            }
            emit(result)
        } catch (e: IOException) {

        }
    }

    override fun getQuickSavedRecipes(): Flow<List<Recipe>> = flow {
        try {
            val result = db.selectQuickRecipes().map { it.toRecipeModel() }
            emit(result)
        } catch (e: IOException) {

        }
    }

    override fun getVeganSavedRecipes(): Flow<List<Recipe>> = flow {
        try {
            val result = db.selectVeganRecipes().map { it.toRecipeModel() }
            emit(result)
        } catch (e: IOException) {

        }
    }

    override fun getCheapSavedRecipes(): Flow<List<Recipe>> = flow {
        try {
            val result = db.selectCheapRecipes().map { it.toRecipeModel() }
            emit(result)
        } catch (e: IOException) {

        }
    }

    override fun getHealthySavedRecipes(): Flow<List<Recipe>> = flow {
        try {
            val result = db.selectHealthyRecipes().map { it.toRecipeModel() }
            emit(result)
        } catch (e: IOException) {

        }
    }

}