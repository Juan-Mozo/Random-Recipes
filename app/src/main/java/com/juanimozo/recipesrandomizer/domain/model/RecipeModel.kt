package com.juanimozo.recipesrandomizer.domain.model

object RecipeModel {
    val veganRecipe: Recipe = Recipe(id = 1, title = "", image = "", instructions = "", servings =  0, readyInMinutes = 40, extendedIngredients = emptyList(), false, vegan = true, vegetarian = false, glutenFree = false, healthScore =  0.0, pairedWineText = "")
    val healthyRecipe: Recipe = Recipe(id = 2, title = "", image = "", instructions = "", servings =  0, readyInMinutes = 40, extendedIngredients = emptyList(), false, vegan = false, vegetarian = false, glutenFree = false, healthScore =  40.0, pairedWineText = "")
    val quickRecipe: Recipe = Recipe(id = 3, title = "", image = "", instructions = "", servings =  0, readyInMinutes = 25, extendedIngredients = emptyList(), false, vegan = false, vegetarian = false, glutenFree = false, healthScore =  0.0, pairedWineText = "")
    val cheapRecipe: Recipe = Recipe(id = 716429, title = "", image = "", instructions = "", servings =  0, readyInMinutes = 0, extendedIngredients = emptyList(), true, vegan = false, vegetarian = false, glutenFree = false, healthScore =  0.0, pairedWineText = "")
    // ToDo:: -Model- *1* / Priority: M
    // Description: Fill completeRecipe model
    val completeRecipe: Recipe = Recipe(id = 716429, title = "Grilled Guacamole with Pistachios", image = null, instructions = "<ol><li>Lightly brush the avocado flesh, corn, onion, and japaleno peppers with olive oil</li></ol>", servings =  8, readyInMinutes = 45, extendedIngredients = emptyList(), false, vegan = true, vegetarian = true, glutenFree = true, healthScore =  35.5, pairedWineText = null)
}