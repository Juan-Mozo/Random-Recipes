package com.juanimozo.recipesrandomizer.domain.model.extended_ingredient

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MeasuresModel(
    val metric: MetricModel,
    val us: UsModel
) : Parcelable
