package com.dvt.weatherapp.common.model

import com.dvt.weatherapp.data.response.CurrentWeatherResponse

data class WeatherWithForecastModel(
    val weather: CurrentWeatherResponse,
    val forecastList: List<CurrentWeatherResponse>,
    var favorite: Boolean
)