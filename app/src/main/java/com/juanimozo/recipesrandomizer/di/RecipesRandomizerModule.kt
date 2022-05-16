package com.juanimozo.recipesrandomizer.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.juanimozo.recipesrandomizer.data.local.Converters
import com.juanimozo.recipesrandomizer.data.local.RecipeDatabase
import com.juanimozo.recipesrandomizer.data.remote.SpoonacularApi
import com.juanimozo.recipesrandomizer.data.repository.RecipesRepositoryImpl
import com.juanimozo.recipesrandomizer.data.util.GsonParser
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

    // Spoonacular Api
    @Provides
    @Singleton
    fun provideSpoonacularApi(): SpoonacularApi {
        return Retrofit.Builder()
            .baseUrl(SpoonacularApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpoonacularApi::class.java)
    }

    // Room Database
    @Provides
    @Singleton
    fun provideRecipeDatabase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(
            app, RecipeDatabase::class.java, "recipe.db"
        )
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    // Repository
    @Provides
    @Singleton
    fun provideRecipesRepository(
        api: SpoonacularApi,
        db: RecipeDatabase
    ): RecipesRepository {
        return RecipesRepositoryImpl(api, db.dao)
    }

    // Use Cases
    @Provides
    @Singleton
    fun provideRecipeUseCases(repository: RecipesRepository): RecipeUseCases {
        return RecipeUseCases(
            getRandomRecipesUseCase = GetRandomRecipesUseCase(repository),
            getRecipeInfoUseCase = GetRecipeInfoUseCase(repository),
            searchRecipeUseCase = SearchRecipeUseCase(repository),
            getSimilarRecipesUseCase = GetSimilarRecipesUseCase(repository),
            deleteRecipeUseCase = DeleteRecipeUseCase(repository),
            getSavedRecipesUseCase = GetSavedRecipesUseCase(repository),
            saveRecipeUseCase = SaveRecipeUseCase(repository)
        )
    }

}