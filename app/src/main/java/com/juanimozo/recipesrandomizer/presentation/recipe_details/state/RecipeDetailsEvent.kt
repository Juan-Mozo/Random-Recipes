package com.juanimozo.recipesrandomizer.presentation.recipe_details.state

import com.juanimozo.recipesrandomizer.domain.model.Recipe

sealed class RecipeDetailsEvent {
    data class MakeFavourite(val recipe: Recipe): RecipeDetailsEvent()
    data class UndoFavourite(val recipe: Recipe): RecipeDetailsEvent()
}
