package com.juanimozo.recipesrandomizer.domain.model

import android.os.Parcelable
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
): Parcelable
