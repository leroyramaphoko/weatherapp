package com.dvt.weatherapp.common.util

import android.content.Context
import com.dvt.weatherapp.R

class TemperatureUtil {
    companion object {
        fun format(context: Context, degree: Double): String {
            return context.getString(R.string.degree_placeholder, degree.toInt().toString())
        }
    }
}