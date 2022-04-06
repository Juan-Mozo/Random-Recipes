package com.juanimozo.recipesrandomizer.presentation.search_recipe

import androidx.lifecycle.ViewModel
import com.juanimozo.recipesrandomizer.presentation.search_recipe.state.SearchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SearchRecipesViewModel @Inject constructor(): ViewModel() {

    private val _searchState = MutableStateFlow(SearchState("", ""))
    val searchState = _searchState.asStateFlow()

    fun onCuisineChanged(cuisine: String) {
        _searchState.value = searchState.value.copy(cuisine = cuisine)
    }

    fun onDietChanged(diet: String) {
        _searchState.value = searchState.value.copy(diet = diet)
    }

}