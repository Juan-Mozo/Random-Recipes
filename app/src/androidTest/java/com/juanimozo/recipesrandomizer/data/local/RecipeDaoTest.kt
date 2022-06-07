package com.juanimozo.recipesrandomizer.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.juanimozo.recipesrandomizer.presentation.util.EmptyModel
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import com.juanimozo.recipesrandomizer.domain.model.RecipeModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class RecipeDaoTest {
    private lateinit var recipeDao: RecipeDao
    private lateinit var db: RecipeDatabase
    private lateinit var recipesToInsert: List<RecipeEntity>

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, RecipeDatabase::class.java).build()
        recipeDao = db.dao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun saveRecipeAndCheckIfInserted() = runBlocking {
        // Insert empty recipe
        val emptyRecipe = EmptyModel.emptyRecipe.toRecipeEntity()
        recipeDao.insertRecipe(emptyRecipe)
        // Check Database
        val recipes = recipeDao.selectAllRecipes()
        assertThat(recipes).contains(emptyRecipe)
    }

    @Test
    @Throws(Exception::class)
    fun getAllRecipes() = runBlocking {
        // Select all recipes
        val allRecipes = recipeDao.selectAllRecipes()
        // Check if inserted recipe is in DB
        assertThat(allRecipes[0]).isEqualTo(RecipeModel)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndDeleteRecipe() = runBlocking {
        // Delete recipe inserted
        recipesToInsert.forEach { recipeDao.deleteRecipe(it.id) }
        // Content of DB
        val recipes = recipeDao.selectAllRecipes()
        // Check if recipe was deleted
        assertThat(recipes).isEmpty()
    }

    @Test
    @Throws(Exception::class)
    fun filterHealthyRecipes() = runBlocking {
        // Select healthy recipes
        val filteredRecipes = recipeDao.selectAllRecipes()
        // Check if inserted recipe is in DB
        assertThat(filteredRecipes[0]).isEqualTo(RecipeModel.healthyRecipe)
    }

    @Test
    @Throws(Exception::class)
    fun filterCheapRecipes() = runBlocking {
        // Select cheap recipes
        val filteredRecipes = recipeDao.selectCheapRecipes()
        // Check if inserted recipe is in DB
        assertThat(filteredRecipes[0]).isEqualTo(RecipeModel.cheapRecipe)
    }

    @Test
    @Throws(Exception::class)
    fun filterQuickRecipes() = runBlocking {
        // Select quick recipes
        val filteredRecipes = recipeDao.selectQuickRecipes()
        // Check if inserted recipe is in DB
        assertThat(filteredRecipes[0]).isEqualTo(RecipeModel.quickRecipe)
    }

    @Test
    @Throws(Exception::class)
    fun filterVeganRecipes() = runBlocking {
        // Select vegan recipes
        val filteredRecipes = recipeDao.selectVeganRecipes()
        // Check if inserted recipe is in DB
        assertThat(filteredRecipes[0]).isEqualTo(RecipeModel.veganRecipe)
    }

}