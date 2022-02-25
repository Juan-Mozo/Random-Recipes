package com.juanimozo.recipesrandomizer.domain.repository

import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.domain.model.*
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    fun getRecipeInfo(id: Int): Flow<Resource<Recipe>>

    fun getRandomRecipes(): Flow<Resource<List<RandomRecipe>>>

    fun searchRecipe(query: String, cuisine: String, diet: String): Flow<Resource<SearchResult>>

    fun getSimilarRecipes(id: Int): Flow<Resource<List<SimilarResults>>>

}