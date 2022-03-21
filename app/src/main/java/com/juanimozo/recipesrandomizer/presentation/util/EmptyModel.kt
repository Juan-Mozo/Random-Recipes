package com.juanimozo.recipesrandomizer.presentation.util

import com.juanimozo.recipesrandomizer.domain.model.Recipe

object EmptyModel {
    val emptyRecipe: Recipe = Recipe(0,"","", "", 0,0, emptyList(), emptyList(), false, false, false, false, 0.0)
}