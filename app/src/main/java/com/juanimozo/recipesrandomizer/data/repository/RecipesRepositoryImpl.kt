package com.juanimozo.recipesrandomizer.data.repository

import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.data.remote.SpoonacularApi
import com.juanimozo.recipesrandomizer.data.remote.dto.search_recipe.Result
import com.juanimozo.recipesrandomizer.domain.model.*
import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val api: SpoonacularApi
): RecipesRepository {

    override fun getRecipeInfo(id: Int): Flow<Resource<Recipe>> = flow {
        emit(Resource.Loading())

        val recipeInfo = api.getRecipeInformation(id).toRecipeEntity()
        emit(Resource.Success(data = recipeInfo))

    }

    override fun getRandomRecipes(): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading())

        val randomRecipes = api.getRandomRecipes().recipes.map { it.toRecipe() }
        emit(Resource.Success(data = randomRecipes))
    }

    override fun searchRecipe(
        query: String,
        cuisine: String,
        diet: String
    ): Flow<Resource<List<Result>>> = flow {
        emit(Resource.Loading())

        val result = api.searchRecipe(
            query = query,
            cuisine = cuisine,
            diet = diet
        ).results
        emit(Resource.Success(data = result))
    }

    override fun getSimilarRecipes(id: Int): Flow<Resource<List<SimilarResults>>> = flow {
        emit(Resource.Loading())

        val similarRecipes = api.getSimilarRecipes(id).map { it.toRecipeEntity() }
        emit(Resource.Success(data = similarRecipes))
    }

}