package com.juanimozo.recipesrandomizer.presentation.util

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

class RecipesListViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    private val recipeTitle = view.findViewById<TextView>(R.id.random_item_title)
    private val recipeImage = view.findViewById<ImageView>(R.id.random_item_image)

    fun bind(item: Recipe) {
        recipeTitle.text = item.title
        if (item.image != null) {
            getImage(
                url = item.image,
                view = recipeImage,
                itemView.context
            )
        }
    }

}

class RecipesAdapter(
    private val listener: (Recipe) -> Unit
): ListAdapter<Recipe, RecipesListViewHolder>(DIFF_CONFIG) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.random_recipe_item, parent, false)
        return RecipesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipesListViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { listener(getItem(position)) }
    }

}