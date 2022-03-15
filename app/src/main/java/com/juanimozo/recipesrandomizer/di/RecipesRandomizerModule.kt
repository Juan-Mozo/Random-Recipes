package com.juanimozo.recipesrandomizer.di

import com.juanimozo.recipesrandomizer.data.remote.SpoonacularApi
import com.juanimozo.recipesrandomizer.data.repository.RecipesRepositoryImpl
import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository
import com.juanimozo.recipesrandomizer.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipesRandomizerModule {

    @Provides
    @Singleton
    fun provideSpoonacularApi(): SpoonacularApi {
        return Retrofit.Builder()
            .baseUrl(SpoonacularApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpoonacularApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipesRepository(
        api: SpoonacularApi
    ): RecipesRepository {
        return RecipesRepositoryImpl(api)
    }
    
    @Provides
    @Singleton
    fun provideRecipeUseCases(repository: RecipesRepository): RecipeUseCases {
        return RecipeUseCases(
            getRandomRecipesUseCase = GetRandomRecipesUseCase(repository),
            getRecipeInfoUseCase = GetRecipeInfoUseCase(repository),
            searchRecipeUseCase = SearchRecipeUseCase(repository),
            getSimilarRecipesUseCase = GetSimilarRecipesUseCase(repository)
        )
    }

}