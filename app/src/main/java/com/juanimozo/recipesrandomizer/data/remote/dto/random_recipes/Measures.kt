package com.juanimozo.recipesrandomizer.data.remote.dto.random_recipes

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Measures(
    val metric: Metric
    // val us: Us
): Parcelable