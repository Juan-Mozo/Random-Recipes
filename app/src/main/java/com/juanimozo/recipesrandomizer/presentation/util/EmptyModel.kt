package com.juanimozo.recipesrandomizer.presentation.util

import com.juanimozo.recipesrandomizer.data.remote.dto.search_recipe.Result
import com.juanimozo.recipesrandomizer.domain.model.Recipe

object EmptyModel {
    val emptyRecipe: Recipe = Recipe(716429,"","", "", 0,0, emptyList(), false, false, false, false, 0.0, "")
    val emptySearchResult: Result = Result(0,"", "", "")
}