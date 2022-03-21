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

    @GET("{id}/information?apiKey=4a64dafdf3b74e4fb0f27ced23b2ca16&includeNutrition=false")
    suspend fun getRecipeInformation(
        @Path("id") id: Int
    ): RecipeInformationDto

    @GET("random?apiKey=4a64dafdf3b74e4fb0f27ced23b2ca16&number=10")
    suspend fun getRandomRecipes(): RandomRecipesDto

    @GET("complexSearch?apiKey=4a64dafdf3b74e4fb0f27ced23b2ca16&query={query}&cuisine={cuisine}&diet={diet}&number=5")
    suspend fun searchRecipe(
        @Query("query") query: String,
        @Query("cuisine") cuisine: String,
        @Query("diet") diet: String
    ): SearchRecipeDto

    @GET("{id}/similar?apiKey=4a64dafdf3b74e4fb0f27ced23b2ca16&number=5")
    suspend fun getSimilarRecipes(
        @Path("id") id: Int
    ): SimilarRecipesDto

    companion object {
        const val BASE_URL = "https://api.spoonacular.com/recipes/"
        const val API_KEY = BuildConfig.API_KEY
    }
}