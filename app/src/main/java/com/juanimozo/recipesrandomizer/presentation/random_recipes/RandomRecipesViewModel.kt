package com.juanimozo.recipesrandomizer.presentation.random_recipes

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

@HiltViewModel
class RandomRecipesViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {

    private val _randomRecipesState = MutableStateFlow(RandomRecipesState(emptyList(), false))
    val randomRecipesState: StateFlow<RandomRecipesState> = _randomRecipesState

    private var getRandomRecipesJob: Job? = null

    init {
        getRandomRecipes()
    }

    private fun getRandomRecipes() {
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
                            // ToDo:: -VM- *1* / Priority: M
                            // Description: Agregar error
                        }
                    }

                }
        }
    }
}