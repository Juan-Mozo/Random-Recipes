package com.juanimozo.recipesrandomizer.data.remote.dto.similar_recipes

import com.juanimozo.recipesrandomizer.domain.model.SimilarResults

data class SimilarRecipesDtoItem(
    val id: Int,
    val imageType: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String
) {
    fun toRecipeEntity(): SimilarResults {
        return SimilarResults(
            id = id,
            title = title,
            image = null,
            readyInMinutes = readyInMinutes,
        )
    }
}