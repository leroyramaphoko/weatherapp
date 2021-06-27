package com.dvt.weatherapp.data.response

import com.dvt.weatherapp.common.enums.WeatherConditionCluster
import com.dvt.weatherapp.common.model.Weather
import com.dvt.weatherapp.common.model.WeatherMain
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("dt") val dateTimeUnix: Long,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val main: WeatherMain
) {
    fun getWeatherConditionCluster(): WeatherConditionCluster? {
        if (weather.isEmpty()) return null

        return WeatherConditionCluster.from(weather.first().id)
    }
}