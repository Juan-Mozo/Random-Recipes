package com.juanimozo.recipesrandomizer.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.juanimozo.recipesrandomizer.data.repository.FakeRecipesRepository
import com.juanimozo.recipesrandomizer.domain.model.RecipeModel
import com.juanimozo.recipesrandomizer.presentation.saved_recipes.RecipeFilter
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSavedRecipesUseCaseTest {

    private lateinit var getSavedRecipes: GetSavedRecipesUseCase
    private lateinit var fakeRepository: FakeRecipesRepository

    @Before
    fun setUp() {
        fakeRepository = FakeRecipesRepository()
        getSavedRecipes = GetSavedRecipesUseCase(fakeRepository)

        val recipesToInsert = mutableListOf(
            RecipeModel.cheapRecipe,
            RecipeModel.veganRecipe,
            RecipeModel.quickRecipe
        )

        runBlocking {
            recipesToInsert.forEach { fakeRepository.saveRecipe(it) }
        }
    }

    @Test
    fun `Filter saved recipes where Healthy is true`() = runBlocking {
        fakeRepository.saveRecipe(RecipeModel.healthyRecipe)
        val recipes = getSavedRecipes.invoke(RecipeFilter.HealthyRecipes())
        assertThat(recipes).isEqualTo(RecipeModel.healthyRecipe)
    }

}