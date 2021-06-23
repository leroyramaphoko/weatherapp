package com.dvt.weatherapp.common.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import com.dvt.weatherapp.common.extension.showIf

@BindingAdapter(value = ["showIf"], requireAll = true)
fun setShowIf(view: View, showIf: Boolean) {
    view.showIf(showIf)
}