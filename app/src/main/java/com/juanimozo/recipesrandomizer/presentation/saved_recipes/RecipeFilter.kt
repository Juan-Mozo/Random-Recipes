package com.juanimozo.recipesrandomizer.presentation.saved_recipes

sealed class RecipeFilter() {
    class AllRecipes: RecipeFilter()
    class QuickRecipes: RecipeFilter()
    class CheapRecipes: RecipeFilter()
    class VeganRecipes: RecipeFilter()
    class HealthyRecipes: RecipeFilter()
}
