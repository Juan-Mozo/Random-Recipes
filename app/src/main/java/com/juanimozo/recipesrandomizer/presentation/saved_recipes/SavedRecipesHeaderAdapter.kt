package com.juanimozo.recipesrandomizer.presentation.saved_recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.juanimozo.recipesrandomizer.R

class SavedRecipesHeaderViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    private val headerImage = view.findViewById<ImageView>(R.id.savedRecipesHeaderImage)

    fun bind(
        filter: RecipeFilter
    ) {
        when (filter) {
            is RecipeFilter.QuickRecipes -> {
                headerImage.setImageResource(R.drawable.quick_recipes_header)
            }
            is RecipeFilter.CheapRecipes -> {
                headerImage.setImageResource(R.drawable.cheap_recipes_header)
            }
            is RecipeFilter.VeganRecipes -> {
                headerImage.setImageResource(R.drawable.vegan_recipes_header)
            }
            is RecipeFilter.HealthyRecipes -> {
                headerImage.setImageResource(R.drawable.healthy_recipes_header)
            }
            is RecipeFilter.AllRecipes -> {}
        }
    }
}

class SavedRecipesHeaderAdapter(
    private val filter: RecipeFilter
): RecyclerView.Adapter<SavedRecipesHeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecipesHeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_recipes_rv_header, parent, false)
        return SavedRecipesHeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedRecipesHeaderViewHolder, position: Int) {
        holder.bind(filter)
    }

    override fun getItemCount(): Int {
        return 1
    }
}