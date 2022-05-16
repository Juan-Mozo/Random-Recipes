package com.juanimozo.recipesrandomizer.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.juanimozo.recipesrandomizer.data.util.GsonParser
import com.juanimozo.recipesrandomizer.domain.model.extended_ingredient.ExtendedIngredientModel

@ProvidedTypeConverter
class Converters(
    private val jsonParser: GsonParser
) {

    // Json to ExtendedIngredient
    @TypeConverter
    fun fromExtendedIngredientJson(json: String): List<ExtendedIngredientModel> {
        return jsonParser.fromJson<ArrayList<ExtendedIngredientModel>>(
            json,
            object: TypeToken<ArrayList<ExtendedIngredientModel>>(){}.type
        ) ?: emptyList()
    }

    // List<ExtendedIngredient> to Json
    @TypeConverter
    fun toExtendedIngredientJson(extendedIngredient: List<ExtendedIngredientModel>): String {
        return jsonParser.toJson(
            extendedIngredient,
            object: TypeToken<ArrayList<ExtendedIngredientModel>>(){}.type
        ) ?: "[]"
    }

}