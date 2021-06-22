package com.dvt.weatherapp.data.api.helper

import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.dvt.weatherapp.data.api.service.ApiService
import retrofit2.Response
import javax.inject.Inject

class ApiHelper @Inject constructor(
    private val apiService: ApiService
): IApiHelper {
    override suspend fun getWeather(): Response<CurrentWeatherResponse> = apiService.getWeather()
    override suspend fun getForecasts(): Response<List<CurrentWeatherResponse>> = apiService.getForecasts()
}