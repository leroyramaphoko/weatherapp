package com.dvt.weatherapp.common.util

import com.dvt.weatherapp.common.constant.AppSettings
import com.dvt.weatherapp.common.extension.roundTo

class LatLonUtil {
    companion object {
        fun format(input: Double): String {
            return input.roundTo(AppSettings.PREFFERED_LAT_LON_DECIMALS).toString()
        }
    }
}