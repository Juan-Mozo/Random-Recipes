package com.juanimozo.recipesrandomizer.data.remote.dto.random_recipe

import android.os.Parcelable
import com.juanimozo.recipesrandomizer.domain.model.extended_ingredient.ExtendedIngredientModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtendedIngredient(
    val aisle: String,
    val amount: Double,
    val consistency: String,
    val id: Int,
    val image: String,
    val measures: Measures,
    val meta: List<String>,
    val name: String,
    val nameClean: String,
    val original: String,
    val originalName: String,
    val unit: String
) : Parcelable {
    fun toInformationExtendedIngredient(): ExtendedIngredientModel {
        return ExtendedIngredientModel(
            aisle = aisle,
            amount = amount,
            consistency = consistency,
            id = id,
            image = image,
            measures = measures.toMeasuresModel(),
            meta = meta,
            name = name,
            nameClean = nameClean,
            original = original,
            originalName = originalName,
            unit = unit
        )
    }
}