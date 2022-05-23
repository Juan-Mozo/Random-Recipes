package com.juanimozo.recipesrandomizer.presentation.search_recipe.recycler_view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.domain.use_case.RecipeUseCases
import com.juanimozo.recipesrandomizer.presentation.search_recipe.state.SearchRecipesState
import com.juanimozo.recipesrandomizer.presentation.util.EmptyModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchRecipeRV ViewModel"

@HiltViewModel
class SearchRecipeRVViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {

    private val _searchRecipeState = MutableStateFlow(SearchRecipesState(emptyList(), false, false))
    val searchRecipeState = _searchRecipeState.asStateFlow()

    private val _newRecipe = MutableSharedFlow<Recipe>()
    val newRecipe = _newRecipe.asSharedFlow()

    private val _internetConnection = MutableStateFlow(false)
    val internetConnection: StateFlow<Boolean> = _internetConnection

    private var searchRecipeJob: Job? = null
    private var getRecipeInfoJob: Job? = null

    fun searchRecipes(
        query: String,
        cuisine: String,
        diet: String
    ) {
        searchRecipeJob?.cancel()
        searchRecipeJob = viewModelScope.launch {
            recipeUseCases.searchRecipeUseCase(query, cuisine, diet)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _searchRecipeState.value = searchRecipeState.value.copy(
                                recipes = result.data ?: emptyList(),
                                isLoading = false,
                                areRecipesLoaded = true
                            )
                        }
                        is Resource.Loading -> {
                            _searchRecipeState.value = searchRecipeState.value.copy(
                                recipes = result.data ?: emptyList(),
                                isLoading = true,
                                areRecipesLoaded = false
                            )
                        }
                        is Resource.Error -> {
                            _searchRecipeState.value = searchRecipeState.value.copy(
                                recipes = result.data ?: emptyList(),
                                isLoading = false,
                                areRecipesLoaded = false
                            )
                            Log.e(TAG, "An error occurred while loading search recipes. Result: ${result.data}")
                        }
                    }
                }
        }
    }

    fun getRecipeInformation(id: Int) {
        getRecipeInfoJob?.cancel()
        getRecipeInfoJob = viewModelScope.launch {
            recipeUseCases.getRecipeInfoUseCase(id)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _newRecipe.emit(result.data ?: EmptyModel.emptyRecipe)
                        }
                        is Resource.Loading -> {}
                        is Resource.Error -> {
                            Log.e(TAG, "An error occurred while getting recipe information. Result: ${result.data}")
                        }
                    }

                }
        }
    }

    fun handleInternetConnection(isInternetConnected: Boolean) {
        _internetConnection.value = isInternetConnected
    }

}