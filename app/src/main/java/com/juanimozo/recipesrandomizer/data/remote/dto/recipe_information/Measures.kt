package com.juanimozo.recipesrandomizer.data.remote.dto.recipe_information

import android.os.Parcelable
import com.juanimozo.recipesrandomizer.domain.model.extended_ingredient.MeasuresModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Measures(
    val metric: Metric,
    val us: Us
) : Parcelable {
    fun toMeasuresModel(): MeasuresModel {
        return MeasuresModel(
            metric = metric.toMetricModel(),
            us = us.toUsModel()
        )
    }
}