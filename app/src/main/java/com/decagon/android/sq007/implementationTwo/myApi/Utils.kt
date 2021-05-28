package com.decagon.android.sq007.implementationTwo.myApi

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(message: String) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).also {
        snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}
