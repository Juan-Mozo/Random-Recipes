package com.juanimozo.recipesrandomizer.data.repository

import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.data.local.RecipeEntity
import com.juanimozo.recipesrandomizer.data.remote.dto.search_recipe.Result
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.domain.model.SimilarResults
import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class FakeRecipesRepository : RecipesRepository {

    private val savedRecipes = mutableListOf<Recipe>()
    private val savedResults = mutableListOf<Result>()
    private val savedSimilarResults = mutableListOf<SimilarResults>()

    override fun getRecipeInfo(id: Int): Flow<Resource<Recipe>> {
        return flow {
            for (recipe in savedRecipes) {
                if (recipe.id == id) {
                    emit(Resource.Success(recipe))
                }
            }
        }
    }

    override fun getRandomRecipes(): Flow<Resource<List<Recipe>>> {
        return flow {
            // Shuffle Recipes
            val shuffledRecipes = savedRecipes
            shuffledRecipes.shuffle()
            // Create list of random recipes
            val randomRecipes = mutableListOf<Recipe>()
            for (i in 0..3) {
                randomRecipes.add(shuffledRecipes[i])
            }
            emit(Resource.Success(randomRecipes))
        }
    }

    override fun searchRecipe(query: String, cuisine: String, diet: String): Flow<Resource<List<Result>>> {
        return flow {
            emit(Resource.Success(savedResults))
        }
    }

    override fun getSimilarRecipes(id: Int): Flow<Resource<List<SimilarResults>>> {
        return flow {
            emit(Resource.Success(savedSimilarResults))
        }
    }

    override suspend fun saveRecipe(recipe: Recipe) {
        savedRecipes.add(recipe)
    }

    override suspend fun deleteRecipe(id: Int) {
        for (recipe in savedRecipes) {
            if (recipe.id == id) {
                savedRecipes.remove(recipe)
            }
        }
    }

    override fun getAllSavedRecipes(): Flow<List<Recipe>> {
        return flow { emit(savedRecipes) }
    }

    override fun getQuickSavedRecipes(): Flow<List<Recipe>> {
        return flow {
            val quickRecipes = mutableListOf<Recipe>()
            for (recipe in savedRecipes) {
                if (recipe.readyInMinutes <= 45) {
                    quickRecipes.add(recipe)
                }
            }
            emit(quickRecipes)
        }
    }

    override fun getVeganSavedRecipes(): Flow<List<Recipe>> {
        return flow {
            val veganRecipes = mutableListOf<Recipe>()
            for (recipe in savedRecipes) {
                if (recipe.vegan) {
                    veganRecipes.add(recipe)
                }
            }
            emit(veganRecipes)
        }
    }

    override fun getCheapSavedRecipes(): Flow<List<Recipe>> {
        return flow {
            val cheapRecipes = mutableListOf<Recipe>()
            for (recipe in savedRecipes) {
                if (recipe.cheap) {
                    cheapRecipes.add(recipe)
                }
            }
            emit(cheapRecipes)
        }
    }

    override fun getHealthySavedRecipes(): Flow<List<Recipe>> {
        return flow {
            val healthyRecipes = mutableListOf<Recipe>()
            for (recipe in savedRecipes) {
                if (recipe.healthScore > 30) {
                    healthyRecipes.add(recipe)
                }
            }
            emit(healthyRecipes)
        }
    }

    override fun checkIfRecipeIsSaved(id: Int): Flow<Boolean> {
        return flow {
            for (recipe in savedRecipes) {
                if (recipe.id == id) {
                    emit(true)
                } else {
                    emit(false)
                }
            }
        }
    }
}