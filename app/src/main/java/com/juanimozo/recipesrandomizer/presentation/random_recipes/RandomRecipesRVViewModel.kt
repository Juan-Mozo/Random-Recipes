package com.juanimozo.recipesrandomizer.presentation.random_recipes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanimozo.recipesrandomizer.core.util.Resource
import com.juanimozo.recipesrandomizer.domain.use_case.RecipeUseCases
import com.juanimozo.recipesrandomizer.presentation.random_recipes.state.RandomRecipesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RandomRecipesRV ViewModel"

@HiltViewModel
class RandomRecipesRVViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {

    private val _randomRecipesState = MutableStateFlow(RandomRecipesState(emptyList(), false))
    val randomRecipesState: StateFlow<RandomRecipesState> = _randomRecipesState

    private val _internetConnection = MutableStateFlow(false)
    val internetConnection: StateFlow<Boolean> = _internetConnection

    private var getRandomRecipesJob: Job? = null

    fun getRandomRecipes() {
        getRandomRecipesJob?.cancel()
        getRandomRecipesJob = viewModelScope.launch {
            recipeUseCases.getRandomRecipesUseCase()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _randomRecipesState.value = randomRecipesState.value.copy(
                                recipes = result.data ?: emptyList(),
                                isLoading = false,
                                recipesAreLoaded = true
                            )
                        }
                        is Resource.Loading -> {
                            _randomRecipesState.value = randomRecipesState.value.copy(
                                recipes = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                        is Resource.Error -> {
                            _randomRecipesState.value = randomRecipesState.value.copy(
                                recipes = result.data ?: emptyList(),
                                isLoading = false
                            )
                            Log.e(TAG, "An error occurred while getting random recipes. Result: ${result.data}")
                        }
                    }

                }
        }
    }

    fun handleInternetConnection(isInternetConnected: Boolean) {
        _internetConnection.value = isInternetConnected
    }
}