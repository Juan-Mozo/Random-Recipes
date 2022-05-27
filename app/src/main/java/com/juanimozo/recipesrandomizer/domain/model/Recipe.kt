package com.juanimozo.recipesrandomizer.domain.model

import android.os.Parcelable
import com.google.gson.JsonParser
import com.juanimozo.recipesrandomizer.core.util.ConvertBoolean
import com.juanimozo.recipesrandomizer.data.local.RecipeEntity
import com.juanimozo.recipesrandomizer.domain.model.extended_ingredient.ExtendedIngredientModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val image: String?,
    val instructions: String,
    val servings: Int,
    val readyInMinutes: Int,
    val extendedIngredients: List<ExtendedIngredientModel>?,
    val cheap: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val glutenFree: Boolean,
    val healthScore: Double,
    val pairedWineText: String?
): Parcelable {
    fun toRecipeEntity(): RecipeEntity {
        return RecipeEntity(
            id = id,
            title = title,
            image = image,
            instructions = instructions,
            servings = servings,
            readyInMinutes = readyInMinutes,
            extendedIngredients = extendedIngredients ?: emptyList(),
            cheap = ConvertBoolean().booleanToInt(cheap),
            vegan = ConvertBoolean().booleanToInt(vegan),
            vegetarian = ConvertBoolean().booleanToInt(vegetarian),
            glutenFree = ConvertBoolean().booleanToInt(glutenFree),
            healthScore = healthScore.toInt(),
            pairedWineText = pairedWineText
        )
    }
}
