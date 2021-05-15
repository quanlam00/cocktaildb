package com.quanlam.thecocktaildb.ui.detail

import com.quanlam.thecocktaildb.R

class IngredientColorUtil {
    companion object {
        fun getColorForNumber(number: Int): Int {
            return when (number) {
                0 -> R.color.yellow
                1 -> R.color.pale_turquoise
                2 -> R.color.linen
                3 -> R.color.steel_blue
                4 -> R.color.sky_blue
                5 -> R.color.pale_leaf
                6 -> R.color.burly_wood
                7 -> R.color.zinnwaldite
                8 -> R.color.ecru_white
                9 -> R.color.vanilla_ice
                10 -> R.color.sea_green
                11 -> R.color.light_blue
                12 -> R.color.gainsboro
                13 -> R.color.dark_sea_green
                14 -> R.color.pink
                else -> R.color.black
            }
        }

        //Get a combination of color for a set size
        //colorOffset is use to vary color combination
        fun getColorSet(size: Int, colorOffset: Int): IntArray {
            val colorSet = IntArray(size)
            for (i in 0 until size) {
                colorSet[i] = getColorForNumber((i + colorOffset)%15)
            }
            return colorSet
        }
    }
}