package com.juanimozo.recipesrandomizer.presentation.search_recipe

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import com.juanimozo.recipesrandomizer.R
import com.juanimozo.recipesrandomizer.presentation.util.SpinnerType

class SpinnerAdapter {

    fun setAdapter(
        context: Context,
        spinner: Spinner,
        array: Int,
        view: View,
        viewModel: SearchRecipesViewModel,
        spinnerType: SpinnerType
    ): String {

        var currentItem = ""

        ArrayAdapter.createFromResource(context, array, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                currentItem = p0?.getItemAtPosition(p2).toString()
                // When None is selected, it´ll send the api an empty string
                if (currentItem == "None") {
                    currentItem = ""
                } else {
                    // Shows the user the item selected
                    Snackbar.make(view, currentItem, Snackbar.LENGTH_SHORT).show()
                }
                // Depending on the spinner, change the value of state in VM
                when (spinnerType) {
                    is SpinnerType.Cuisine -> {
                        viewModel.onCuisineChanged(currentItem)
                    }
                    is SpinnerType.Diet -> {
                        viewModel.onDietChanged(currentItem)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                println("Error")
            }

        }

        return currentItem

    }

}