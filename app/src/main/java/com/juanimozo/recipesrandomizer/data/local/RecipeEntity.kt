package com.juanimozo.recipesrandomizer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juanimozo.recipesrandomizer.core.util.ConvertBoolean
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.domain.model.extended_ingredient.ExtendedIngredientModel

@Entity(tableName = "recipe_table")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val image: String?,
    val instructions: String,
    val servings: Int,
    val readyInMinutes: Int,
    val extendedIngredients: List<ExtendedIngredientModel>,
    val cheap: Int,
    val vegan: Int,
    val vegetarian: Int,
    val glutenFree: Int,
    val healthScore: Int,
    val pairedWineText: String?
) {
    fun toRecipeModel(): Recipe {
        return Recipe(
            id = id,
            title = title,
            image = image,
            instructions = instructions,
            servings = servings,
            readyInMinutes = readyInMinutes,
            extendedIngredients = extendedIngredients,
            cheap = ConvertBoolean().intToBoolean(cheap),
            vegan = ConvertBoolean().intToBoolean(vegan),
            vegetarian = ConvertBoolean().intToBoolean(vegetarian),
            glutenFree = ConvertBoolean().intToBoolean(glutenFree),
            healthScore = healthScore.toDouble(),
            pairedWineText = pairedWineText
        )
    }
}