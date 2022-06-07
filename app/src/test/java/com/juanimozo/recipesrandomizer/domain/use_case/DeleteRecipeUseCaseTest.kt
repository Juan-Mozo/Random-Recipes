package com.juanimozo.recipesrandomizer.domain.use_case

import com.juanimozo.recipesrandomizer.data.repository.FakeRecipesRepository
import com.juanimozo.recipesrandomizer.domain.model.RecipeModel
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class DeleteRecipeUseCaseTest {

    private lateinit var deleteRecipe: DeleteRecipeUseCase
    private lateinit var fakeRepository: FakeRecipesRepository

    @Before
    fun setUp() {
        fakeRepository = FakeRecipesRepository()
        deleteRecipe = DeleteRecipeUseCase(fakeRepository)

        val recipesToInsert = mutableListOf(
            RecipeModel.cheapRecipe,
            RecipeModel.veganRecipe
        )

        runBlocking {
            recipesToInsert.forEach { recipe -> fakeRepository.saveRecipe(recipe) }
        }
    }

    @Test
    fun `Delete recipe  by id`() = runBlocking {
        // Delete vegan recipe
        deleteRecipe.invoke(RecipeModel.veganRecipe.id)
        // Get all saved recipes
        val recipes = fakeRepository.getAllSavedRecipes()
        assertThat(recipes).isEqualTo(RecipeModel.cheapRecipe)
    }
}