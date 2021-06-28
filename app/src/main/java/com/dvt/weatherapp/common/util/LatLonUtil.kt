package com.dvt.weatherapp.common.util

import com.dvt.weatherapp.common.constant.AppConstants
import com.google.android.gms.maps.model.LatLng

class LatLonUtil {
    companion object {
        fun format(input: Double): String {
            return round(input).toString()
        }

        fun limitDecimalsOnLatLng(latLng: LatLng): LatLng {
            return LatLng(
                round(latLng.latitude),
                round(latLng.longitude)
            )
        }

        fun round(input: Double): Double {
            val decimals = AppConstants.PREFERRED_LAT_LON_DECIMALS
            return String.format("%.${decimals}f", input).toDouble()
        }
    }
}