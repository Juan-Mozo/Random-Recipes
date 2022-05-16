package com.juanimozo.recipesrandomizer.presentation.saved_recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juanimozo.recipesrandomizer.R
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.presentation.util.getImage

class SavedRecipesViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    private val recipeName = view.findViewById<TextView>(R.id.saved_recipe_item_text)
    private val recipeImage = view.findViewById<ImageView>(R.id.saved_recipe_item_image)

    fun bind(item: Recipe) {
        recipeName.text = item.title
        if(item.image != null) {
            getImage(
                url = item.image,
                view = recipeImage,
                itemView.context
            )
        }
    }
}

class SavedRecipesAdapter(
    private val listener: (Recipe) -> Unit
): ListAdapter<Recipe, SavedRecipesViewHolder>(DIFF_CONFIG) {

    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecipesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_recipes_item, parent, false)
        return SavedRecipesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedRecipesViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { listener(getItem(position)) }
    }
}