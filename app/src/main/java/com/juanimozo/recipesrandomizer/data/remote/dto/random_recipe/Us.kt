package com.juanimozo.recipesrandomizer.data.remote.dto.random_recipe

import android.os.Parcelable
import com.juanimozo.recipesrandomizer.domain.model.extended_ingredient.UsModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Us(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
) : Parcelable {
    fun toUsModel(): UsModel {
        return UsModel(
            amount = amount,
            unitLong = unitLong,
            unitShort = unitShort
        )
    }
}