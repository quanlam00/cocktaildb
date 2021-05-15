package com.quanlam.thecocktaildb.ui.detail

import androidx.lifecycle.ViewModel
import com.quanlam.thecocktaildb.dataservice.CocktailDBService
import com.quanlam.thecocktaildb.dataservice.model.Drink
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CocktailDetailViewModel : ViewModel() {
    var drink: Drink? = null
    var disposable: Disposable? = null

    fun getDrinkDetail(
        drinkId: String,
        onDrinkFetched: ()->Unit,
    ) {
        disposable = CocktailDBService.getAPI()
            .getCocktailById(drinkId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (!it.drinks.isNullOrEmpty())
                    drink = it.drinks.first()
                onDrinkFetched()
            }, {
                it.printStackTrace()
            })
    }

    fun onDestroy() {
        disposable?.dispose()
    }
}