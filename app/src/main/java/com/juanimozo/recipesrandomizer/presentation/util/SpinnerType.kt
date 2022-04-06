package com.juanimozo.recipesrandomizer.presentation.util

sealed class SpinnerType() {
    class Cuisine: SpinnerType()
    class Diet: SpinnerType()
}
