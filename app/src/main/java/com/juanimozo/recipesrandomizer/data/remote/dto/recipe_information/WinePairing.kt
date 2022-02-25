package com.juanimozo.recipesrandomizer.data.remote.dto.recipe_information

data class WinePairing(
    val pairedWines: List<Any>,
    val pairingText: String,
    val productMatches: List<Any>
)