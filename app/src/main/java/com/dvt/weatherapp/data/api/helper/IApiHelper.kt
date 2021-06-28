package com.dvt.weatherapp.data.api.helper

import com.dvt.weatherapp.data.response.WeatherResponse
import com.dvt.weatherapp.data.response.ForecastResponse
import retrofit2.Response

interface IApiHelper {

    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): Response<WeatherResponse>

    suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Response<ForecastResponse>

}