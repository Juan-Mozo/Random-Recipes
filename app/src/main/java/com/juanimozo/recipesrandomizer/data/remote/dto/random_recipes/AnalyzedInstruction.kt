package com.juanimozo.recipesrandomizer.data.remote.dto.random_recipes

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)