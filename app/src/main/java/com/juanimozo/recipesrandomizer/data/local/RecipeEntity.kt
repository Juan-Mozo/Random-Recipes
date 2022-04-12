package com.juanimozo.recipesrandomizer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.domain.model.extended_ingredient.ExtendedIngredientModel

@Entity
data class RecipeEntity(
    @PrimaryKey val id: Int,
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
            cheap = cheap,
            vegan = vegan,
            vegetarian = vegetarian,
            glutenFree = glutenFree,
            healthScore = healthScore,
            pairedWineText = pairedWineText
        )
    }
}