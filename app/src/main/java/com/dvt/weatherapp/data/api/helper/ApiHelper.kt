package com.dvt.weatherapp.data.api.helper

import com.dvt.weatherapp.data.api.service.ApiService
import com.dvt.weatherapp.data.response.WeatherResponse
import com.dvt.weatherapp.data.response.ForecastResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelper @Inject constructor(
    private val apiService: ApiService
): IApiHelper {

    override suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): Response<WeatherResponse> = apiService.getWeather(latitude, longitude)

    override suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Response<ForecastResponse> = apiService.getForecast(latitude, longitude)

}