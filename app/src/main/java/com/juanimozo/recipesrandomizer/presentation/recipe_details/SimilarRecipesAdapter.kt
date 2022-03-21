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
import com.juanimozo.recipesrandomizer.domain.model.SimilarResults
import com.juanimozo.recipesrandomizer.presentation.util.getImage

class SimilarRecipesListViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    private val recipeName = view.findViewById<TextView>(R.id.similar_recipe_item_text)
    private val recipeImage = view.findViewById<ImageView>(R.id.similar_recipe_item_image)

    fun bind(item: SimilarResults) {
        recipeName.text = item.title
        if (item.image != null) {
            getImage(
                url = item.image,
                view = recipeImage,
                itemView.context
            )
        }
    }
}

class SimilarRecipesAdapter(
    private val listener: (SimilarResults) -> Unit
): ListAdapter<SimilarResults, SimilarRecipesListViewHolder>(DIFF_CONFIG) {

    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<SimilarResults>() {
            override fun areItemsTheSame(oldItem: SimilarResults, newItem: SimilarResults): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: SimilarResults, newItem: SimilarResults): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarRecipesListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.similar_recipes_item, parent, false)
        return SimilarRecipesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimilarRecipesListViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { listener(getItem(position)) }
    }
}