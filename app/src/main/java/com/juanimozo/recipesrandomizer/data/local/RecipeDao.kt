package com.juanimozo.recipesrandomizer.data.local

import androidx.room.*

@Dao
interface RecipeDao {

    // Insert new recipe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    // Delete recipe
    @Query("DELETE FROM recipeentity WHERE id IN(:id)")
    suspend fun deleteRecipe(id: Int)

    // Select saved recipes
    @Query("SELECT * FROM recipeentity")
    suspend fun selectAllRecipes()

}