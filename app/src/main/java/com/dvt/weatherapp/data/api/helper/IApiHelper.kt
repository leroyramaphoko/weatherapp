package com.dvt.weatherapp.data.api.helper

import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.dvt.weatherapp.data.response.ForecastResponse
import retrofit2.Response

interface IApiHelper {
    suspend fun getWeather(): Response<CurrentWeatherResponse>
    suspend fun getForecast(): Response<ForecastResponse>
}