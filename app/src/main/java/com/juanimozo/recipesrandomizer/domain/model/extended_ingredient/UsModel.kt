package com.juanimozo.recipesrandomizer.domain.model.extended_ingredient

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsModel(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
) : Parcelable
