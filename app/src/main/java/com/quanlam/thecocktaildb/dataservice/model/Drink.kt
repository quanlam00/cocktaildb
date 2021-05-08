package com.quanlam.thecocktaildb.dataservice.model

import com.google.gson.annotations.SerializedName
import com.quanlam.thecocktaildb.ui.IngredientListItem

data class Drink(
    val dateModified: String?,
    val idDrink: String?,
    val strAlcoholic: String?,
    val strCategory: String?,
    val strCreativeCommonsConfirmed: String?,
    val strDrink: String?,
    val strDrinkAlternate: String?,
    val strDrinkThumb: String?,
    val strGlass: String?,
    val strIBA: String?,
    val strImageAttribution: String?,
    val strImageSource: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strInstructions: String?,
    val strInstructionsES: String?,
    val strInstructionsDE: String?,
    val strInstructionsFR: String,
    @SerializedName("strInstructionsZH-HANS")
    val strInstructionsZHHANS: String?,
    @SerializedName("strInstructionsZH-HANT")
    val strInstructionsZHHANT: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strTags: String?,
    val strVideo: String?
) {
    private var ingredientList: ArrayList<IngredientListItem>? = null
    //Parse the default ingredient structure into a list
    fun getIngredientsList(): List<IngredientListItem> {
        if (ingredientList == null) {
            ingredientList = ArrayList()
            if (!strIngredient1.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient1,
                    strMeasure1 ?: ""
                )
            )
            if (!strIngredient2.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient2,
                    strMeasure2 ?: ""
                )
            )
            if (!strIngredient3.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient3,
                    strMeasure3 ?: ""
                )
            )
            if (!strIngredient4.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient4,
                    strMeasure4 ?: ""
                )
            )
            if (!strIngredient5.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient5,
                    strMeasure5 ?: ""
                )
            )
            if (!strIngredient6.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient6,
                    strMeasure6 ?: ""
                )
            )
            if (!strIngredient7.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient7,
                    strMeasure7 ?: ""
                )
            )
            if (!strIngredient8.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient8,
                    strMeasure8 ?: ""
                )
            )
            if (!strIngredient9.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient9,
                    strMeasure9 ?: ""
                )
            )
            if (!strIngredient10.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient10,
                    strMeasure10 ?: ""
                )
            )
            if (!strIngredient11.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient11,
                    strMeasure11 ?: ""
                )
            )
            if (!strIngredient12.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient12,
                    strMeasure12 ?: ""
                )
            )
            if (!strIngredient13.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient13,
                    strMeasure13 ?: ""
                )
            )
            if (!strIngredient14.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient14,
                    strMeasure14 ?: ""
                )
            )
            if (!strIngredient15.isNullOrEmpty()) ingredientList!!.add(
                IngredientListItem(
                    strIngredient15,
                    strMeasure15 ?: ""
                )
            )
        }
        return ingredientList!!
    }
}

