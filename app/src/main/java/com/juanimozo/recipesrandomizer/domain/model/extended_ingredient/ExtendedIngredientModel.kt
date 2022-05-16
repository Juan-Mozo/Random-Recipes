package com.juanimozo.recipesrandomizer.domain.model.extended_ingredient

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtendedIngredientModel(
    val amount: Double,
    val measures: MeasuresModel,
    val originalName: String
) : Parcelable
