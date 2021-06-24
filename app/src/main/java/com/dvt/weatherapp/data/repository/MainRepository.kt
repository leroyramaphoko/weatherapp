package com.dvt.weatherapp.data.repository

import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.dvt.weatherapp.data.api.helper.IApiHelper
import com.dvt.weatherapp.data.response.ForecastResponse
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: IApiHelper
) {
    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): Response<CurrentWeatherResponse> = apiHelper.getWeather(latitude, longitude)
    suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Response<ForecastResponse> = apiHelper.getForecast(latitude, longitude)
}