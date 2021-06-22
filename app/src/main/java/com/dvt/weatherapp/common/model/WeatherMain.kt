package com.dvt.weatherapp.common.model

import com.google.gson.annotations.SerializedName

data class WeatherMain(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("temp_min") val temperatureMin: Double,
    @SerializedName("temp_max") val temperatureMax: Double
)
