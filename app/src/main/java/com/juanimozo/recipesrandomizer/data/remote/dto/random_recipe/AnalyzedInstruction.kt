package com.juanimozo.recipesrandomizer.data.remote.dto.random_recipe

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)