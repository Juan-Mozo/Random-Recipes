package com.juanimozo.recipesrandomizer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RecipeEntity::class],
    version = 1
)
abstract class RecipeDatabase: RoomDatabase() {
    abstract val dao: RecipeDao
}