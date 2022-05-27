package com.juanimozo.recipesrandomizer.data.remote.dto

import com.juanimozo.recipesrandomizer.data.remote.dto.recipe_information.ExtendedIngredient
import com.juanimozo.recipesrandomizer.data.remote.dto.recipe_information.WinePairing
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.data.util.StringConfig

data class RecipeInformationDto(
    val aggregateLikes: Int,
    val analyzedInstructions: List<Any>,
    val cheap: Boolean,
    val creditsText: String,
    val cuisines: List<Any>,
    val dairyFree: Boolean,
    val diets: List<Any>,
    val dishTypes: List<String>,
    val extendedIngredients: List<ExtendedIngredient>,
    val gaps: String,
    val glutenFree: Boolean,
    val healthScore: Double,
    val id: Int,
    val image: String,
    val imageType: String,
    val instructions: String,
    val license: String,
    val lowFodmap: Boolean,
    val occasions: List<Any>,
    val originalId: Any,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String,
    val summary: String,
    val sustainable: Boolean,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int,
    val winePairing: WinePairing
) {
    fun toRecipeEntity(): Recipe {
        return Recipe(
            id = id,
            title = title,
            image = image,
            instructions = StringConfig().replaceHTMLTags(instructions),
            servings = servings,
            readyInMinutes = readyInMinutes,
            extendedIngredients = extendedIngredients.map { it.toInformationExtendedIngredient() },
            cheap = cheap,
            vegan = vegan,
            vegetarian = vegetarian,
            glutenFree = glutenFree,
            healthScore = healthScore,
            pairedWineText = winePairing.pairingText
        )
    }
}