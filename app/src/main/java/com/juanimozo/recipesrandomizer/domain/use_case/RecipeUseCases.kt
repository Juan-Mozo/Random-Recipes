package com.juanimozo.recipesrandomizer.domain.use_case

data class RecipeUseCases(
    val getRandomRecipesUseCase: GetRandomRecipesUseCase,
    val getRecipeInfoUseCase: GetRecipeInfoUseCase,
    val searchRecipeUseCase: SearchRecipeUseCase,
    val getSimilarRecipesUseCase: GetSimilarRecipesUseCase,
    val saveRecipeUseCase: SaveRecipeUseCase,
    val deleteRecipeUseCase: DeleteRecipeUseCase,
    val getSavedRecipesUseCase: GetSavedRecipesUseCase,
    val checkIfRecipesIsSavedUseCase: CheckIfRecipesIsSavedUseCase
)