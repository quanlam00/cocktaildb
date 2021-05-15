package com.quanlam.thecocktaildb.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quanlam.thecocktaildb.dataservice.CocktailDBService
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val drinkList = ArrayList<CocktailListItem>()
    private val filteredList = ArrayList<CocktailListItem>()

    //LiveData is used to broadcast changes from ViewModel to UI elements
    val liveDrinkList: MutableLiveData<List<CocktailListItem>> = MutableLiveData(drinkList)
    val liveFilteredList: MutableLiveData<List<CocktailListItem>> = MutableLiveData(filteredList)
    val liveLoadingState = MutableLiveData(false)
    private val compositeDisposable = CompositeDisposable()

    var currentAlphabet = 'a'

    //Lock the viewModel from making more alphabet calls while one call is on going
    var getMoreLock = false

    //Get additional cocktail by alphabet to add to the full list of drinks
    //Each call will bring current alphabet to next character
    @Synchronized
    fun getMoreCocktails() {
        liveLoadingState.postValue(true)
        synchronized(getMoreLock) {
            if (currentAlphabet <= 'z' && !getMoreLock) {
                getMoreLock = true
                val disposable = CocktailDBService.getAPI()
                    .getAlphabetCocktails(currentAlphabet)
                    .observeOn(Schedulers.io())
                    .subscribe({ response ->
                        synchronized(getMoreLock) {
                            if (response.drinks != null) {
                                //Map drink data item to drink list item
                                response.drinks.forEach {
                                    drinkList.add(
                                        CocktailListItem(
                                            it.strDrink ?: "",
                                            it.strDrinkThumb ?: "",
                                            it.idDrink ?: ""
                                        )
                                    )
                                }
                            }
                            liveDrinkList.postValue(drinkList)
                            getMoreLock = false
                            liveLoadingState.postValue(false)
                        }
                    }, {
                        it.printStackTrace()
                    })
                compositeDisposable.add(disposable)
                currentAlphabet++
            }
        }
    }

    // currentSearch is used to invalidate the data from backend.
    // Only when current search word match the word in the call that a UI change is required.
    // Otherwise ignore the data.
    private var currentSearch = ""
    fun searchCocktailByName(name: String) {
        liveLoadingState.postValue(true)
        synchronized(currentSearch) {
            currentSearch = name
            val disposable =
                CocktailDBService.getAPI()
                    .searchCocktailByName(name)
                    .observeOn(Schedulers.io())
                    .subscribe({ response ->
                        synchronized(currentSearch) {
                            if (currentSearch == name) {
                                filteredList.clear()
                                //Map drink data item to drink list item
                                response.drinks?.forEach {
                                    filteredList.add(
                                        CocktailListItem(
                                            it.strDrink ?: "",
                                            it.strDrinkThumb ?: "",
                                            it.idDrink ?: ""
                                        )
                                    )
                                }
                                liveFilteredList.postValue(filteredList)
                                liveLoadingState.postValue(false)
                            }
                        }
                    }, {
                        it.printStackTrace()
                    })
            compositeDisposable.add(disposable)
        }
    }

    fun cancelSearch() {
        currentSearch = ""
        liveLoadingState.postValue(false)
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}