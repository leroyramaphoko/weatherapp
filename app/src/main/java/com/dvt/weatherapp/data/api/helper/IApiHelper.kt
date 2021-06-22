package com.dvt.weatherapp.data.api.helper

import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import retrofit2.Response

interface IApiHelper {
    suspend fun getWeather(): Response<CurrentWeatherResponse>
    suspend fun getForecasts(): Response<List<CurrentWeatherResponse>>
}