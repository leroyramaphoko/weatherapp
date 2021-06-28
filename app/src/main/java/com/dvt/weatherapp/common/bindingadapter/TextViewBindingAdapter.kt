package com.dvt.weatherapp.common.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dvt.weatherapp.common.util.TemperatureUtil

@BindingAdapter(value = ["degree"], requireAll = true)
fun setDegreeCelsius(textView: TextView, degree: Double) {
    textView.text = TemperatureUtil.format(textView.context, degree)
}