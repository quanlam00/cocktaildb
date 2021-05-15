package com.quanlam.thecocktaildb.ui.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun Fragment.setUpProgressBar(liveData: LiveData<Boolean>) {
    val progressBarObserver = Observer<Boolean> {
        if (it) {
            showProgress()
        } else {
            hideProgress()
        }
    }
    liveData.observe(viewLifecycleOwner, progressBarObserver)
}

fun Fragment.showProgress() {
    this.activity?.showProgress()
}

fun Fragment.hideProgress() {
    this.activity?.hideProgress()
}