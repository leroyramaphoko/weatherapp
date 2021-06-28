package com.dvt.weatherapp.common.constant

import com.dvt.weatherapp.common.enums.TemperatureUnit
import com.google.android.gms.maps.model.LatLng

object AppConstants {
    const val APP_ID = "appid"
    const val UNITS = "units"
    const val LAT = "lat"
    const val LON = "lon"
    val PREFERRED_TEMPERATURE_UNIT = TemperatureUnit.METRIC
    const val PREFERRED_LAT_LON_DECIMALS = 4
    const val DEFAULT_ZOOM_LEVEL = 10
    val DEFAULT_LOCATION = LatLng(-26.125210, 27.931410)
}