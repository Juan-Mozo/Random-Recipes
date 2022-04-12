package com.juanimozo.recipesrandomizer.data.remote.dto.recipe_information

import android.os.Parcelable
import com.juanimozo.recipesrandomizer.domain.model.extended_ingredient.MetricModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Metric(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
) : Parcelable {
    fun toMetricModel(): MetricModel {
        return MetricModel(
            amount = amount,
            unitLong = unitLong,
            unitShort = unitShort
        )
    }
}