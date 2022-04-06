package com.juanimozo.recipesrandomizer.presentation.search_recipe.recycler_view

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

@HiltViewModel
class SearchRecipeRVViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {

    private val _searchRecipe = MutableStateFlow(SearchRecipesState(emptyList(), false))
    val searchRecipe = _searchRecipe.asStateFlow()

    private val _newRecipe = MutableSharedFlow<Recipe>()
    val newRecipe = _newRecipe.asSharedFlow()

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
                            _searchRecipe.value = searchRecipe.value.copy(
                                recipes = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _searchRecipe.value = searchRecipe.value.copy(
                                recipes = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                        is Resource.Error -> {
                            // ToDo:: -VM- *1* / Priority: M
                            // Description: Agregar error
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
                            // ToDo:: -VM- *1* / Priority: M
                            // Description: Agregar error
                        }
                    }

                }
        }
    }

}