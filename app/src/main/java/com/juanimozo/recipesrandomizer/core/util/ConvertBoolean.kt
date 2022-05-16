package com.juanimozo.recipesrandomizer.core.util

class ConvertBoolean {

    fun booleanToInt(boolean: Boolean): Int {
        return if (boolean) {
            1
        } else {
            0
        }
    }

    fun intToBoolean(int: Int): Boolean {
        return int == 1
    }

}