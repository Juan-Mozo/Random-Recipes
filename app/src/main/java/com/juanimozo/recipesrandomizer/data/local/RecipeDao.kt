package com.juanimozo.recipesrandomizer.data.local

import androidx.room.*

@Dao
interface RecipeDao {

    // Insert new recipe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    // Delete recipe
    @Query("DELETE FROM recipe_table WHERE id IN(:id)")
    suspend fun deleteRecipe(id: Int)

    // Select saved recipes
    @Query("SELECT * FROM recipe_table ORDER BY title DESC")
    suspend fun selectAllRecipes(): List<RecipeEntity>

    // Select recipes that are made in less than 30 minutes
    @Query("SELECT * FROM recipe_table WHERE readyInMinutes < 30 ORDER BY title DESC")
    suspend fun selectQuickRecipes(): List<RecipeEntity>

    // Select cheap recipes
    @Query("SELECT * FROM recipe_table WHERE cheap = 1 ORDER BY title DESC")
    suspend fun selectCheapRecipes(): List<RecipeEntity>

    // Select vegan recipes
    @Query("SELECT * FROM recipe_table WHERE vegan = 1 ORDER BY title DESC")
    suspend fun selectVeganRecipes(): List<RecipeEntity>

    // Select healthy recipes
    @Query("SELECT * FROM recipe_table WHERE healthScore > 30 ORDER BY title DESC")
    suspend fun selectHealthyRecipes(): List<RecipeEntity>



}