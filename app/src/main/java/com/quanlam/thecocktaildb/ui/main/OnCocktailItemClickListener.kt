package com.quanlam.thecocktaildb.ui.main

import com.quanlam.thecocktaildb.ui.main.CocktailListItem

interface OnCocktailItemClickListener {
    fun onClick(item: CocktailListItem?)
}