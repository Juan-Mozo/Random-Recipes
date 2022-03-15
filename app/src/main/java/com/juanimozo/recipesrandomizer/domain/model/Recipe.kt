package com.juanimozo.recipesrandomizer.domain.model

import android.os.Parcelable
import com.juanimozo.recipesrandomizer.data.remote.dto.random_recipes.ExtendedIngredient
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val image: String?,
    val instructions: String,
    val servings: Int,
    val readyInMinutes: Int,
    // val cuisines: List<Any>?,
    val extendedIngredients: List<ExtendedIngredient>?,
    val dishType: List<String>?,
    val cheap: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val glutenFree: Boolean,
    val healthScore: Double,
    // val pairedWines: List<Any>?,
    // val pairedWineText: String?
): Parcelable
