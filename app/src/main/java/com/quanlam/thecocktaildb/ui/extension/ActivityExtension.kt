package com.quanlam.thecocktaildb.ui.extension

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import com.quanlam.thecocktaildb.R

fun Activity.showProgress() {
    val loadingDialog = this.findViewById<ProgressBar>(R.id.pbLoading)
    loadingDialog?.let {
        loadingDialog.visibility = View.VISIBLE
    }
}

fun Activity.hideProgress() {
    val loadingDialog = this.findViewById<ProgressBar>(R.id.pbLoading)
    loadingDialog?.let {
        loadingDialog.visibility = View.INVISIBLE
    }
}