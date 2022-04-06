package com.juanimozo.recipesrandomizer.presentation.search_recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juanimozo.recipesrandomizer.R
import com.juanimozo.recipesrandomizer.data.remote.dto.search_recipe.Result
import com.juanimozo.recipesrandomizer.domain.model.Recipe
import com.juanimozo.recipesrandomizer.domain.model.SearchResult
import com.juanimozo.recipesrandomizer.presentation.util.getImage

class SearchRecipesListViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    private val recipeTitle = view.findViewById<TextView>(R.id.searched_item_title)
    private val recipeImage = view.findViewById<ImageView>(R.id.searched_item_image)

    fun bind(item: Result) {
        recipeTitle.text = item.title
        if(item.image.isNotBlank()) {
            getImage(
                url = item.image,
                view = recipeImage,
                itemView.context
            )
        }
    }

}

class SearchRecipesAdapter(
    private val listener: (Result) -> Unit
): ListAdapter<Result, SearchRecipesListViewHolder>(DIFF_CONFIG) {

    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecipesListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_recipe_item, parent, false)
        return SearchRecipesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchRecipesListViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { listener(getItem(position)) }
    }

}