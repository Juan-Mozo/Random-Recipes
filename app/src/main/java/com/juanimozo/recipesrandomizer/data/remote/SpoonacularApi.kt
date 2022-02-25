package com.juanimozo.recipesrandomizer.data.remote

import com.juanimozo.recipesrandomizer.BuildConfig
import com.juanimozo.recipesrandomizer.data.remote.dto.RandomRecipesDto
import com.juanimozo.recipesrandomizer.data.remote.dto.RecipeInformationDto
import com.juanimozo.recipesrandomizer.data.remote.dto.SearchRecipeDto
import com.juanimozo.recipesrandomizer.data.remote.dto.SimilarRecipesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApi {

    @GET("/{id}/information?apiKey=$API_KEY&includeNutrition=false")
    suspend fun getRecipeInformation(
        @Path("id") id: Int
    ): RecipeInformationDto

    @GET("/random?apiKey=$API_KEY&number=10")
    suspend fun getRandomRecipes(): RandomRecipesDto

    @GET("/complexSearch?apiKey=$API_KEY&query={query}&cuisine={cuisine}&diet={diet}&number=5")
    suspend fun searchRecipe(
        @Query("query") query: String,
        @Query("cuisine") cuisine: String,
        @Query("diet") diet: String
    ): SearchRecipeDto

    @GET("/{id}/similar?apiKey=$API_KEY&number=5")
    suspend fun getSimilarRecipes(
        @Path("id") id: Int
    ): SimilarRecipesDto

    companion object {
        const val BASE_URL = "https://api.spoonacular.com/recipes/"
        const val API_KEY = BuildConfig.API_KEY
    }
}