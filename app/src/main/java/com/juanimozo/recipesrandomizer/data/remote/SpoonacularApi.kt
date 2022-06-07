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

    // Get complete information of recipe by id
    @GET("{id}/information?apiKey=$API_KEY&includeNutrition=false")
    suspend fun getRecipeInformation(
        @Path("id") id: Int
    ): RecipeInformationDto

    // Get 20 random recipes
    @GET("random?apiKey=$API_KEY&number=20")
    suspend fun getRandomRecipes(): RandomRecipesDto

    // Search 10 recipes sort by popularity, with cuisine and diet as parameters
    @GET("complexSearch?apiKey=$API_KEY&number=40&sort=popularity")
    suspend fun searchRecipe(
        @Query("query") query: String,
        @Query("cuisine") cuisine: String,
        @Query("diet") diet: String
    ): SearchRecipeDto

    // Get a list of 5 recipes that are similar to the current recipe
    @GET("{id}/similar?apiKey=$API_KEY&number=5")
    suspend fun getSimilarRecipes(
        @Path("id") id: Int
    ): SimilarRecipesDto

    companion object {
        const val BASE_URL = "https://api.spoonacular.com/recipes/"
        const val API_KEY = BuildConfig.API_KEY
    }
}