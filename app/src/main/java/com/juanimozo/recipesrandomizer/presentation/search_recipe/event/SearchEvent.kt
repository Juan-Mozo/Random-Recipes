package com.juanimozo.recipesrandomizer.presentation.search_recipe.event

sealed class SearchEvent {
    data class SelectCuisine(val cuisine: SearchEvent): SearchEvent()
    data class SelectDiet(val diet: SearchEvent): SearchEvent()
}
