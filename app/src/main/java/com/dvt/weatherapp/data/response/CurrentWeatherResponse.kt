package com.dvt.weatherapp.data.response

import com.dvt.weatherapp.common.model.Weather
import com.dvt.weatherapp.common.model.WeatherMain
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val main: WeatherMain
)