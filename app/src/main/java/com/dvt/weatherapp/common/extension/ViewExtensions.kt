package com.dvt.weatherapp.common.extension

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showIf(visible: Boolean) {
    if (visible) {
        this.visible()
    } else {
        this.gone()
    }
}