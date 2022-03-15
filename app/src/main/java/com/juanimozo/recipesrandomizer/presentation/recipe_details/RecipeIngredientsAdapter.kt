package com.juanimozo.recipesrandomizer.presentation.recipe_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juanimozo.recipesrandomizer.R
import com.juanimozo.recipesrandomizer.data.remote.dto.random_recipes.ExtendedIngredient

class RecipeIngredientListViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    private val ingredient = view.findViewById<TextView>(R.id.ingredient)

    fun bind(item: ExtendedIngredient) {
        val ingAmount = item.measures.metric.amount
        val ingUnit = item.measures.metric.unitShort
        val ingTitle = item.originalName
        ingredient.text = "${ingAmount} ${ingUnit} ${ingTitle}"
    }

}

class RecipeIngredientsAdapter: ListAdapter<ExtendedIngredient, RecipeIngredientListViewHolder>(DIFF_CONFIG) {

    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<ExtendedIngredient>() {
            override fun areItemsTheSame(oldItem: ExtendedIngredient, newItem: ExtendedIngredient): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ExtendedIngredient, newItem: ExtendedIngredient): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeIngredientListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_ingredient_item, parent, false)
        return RecipeIngredientListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeIngredientListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}