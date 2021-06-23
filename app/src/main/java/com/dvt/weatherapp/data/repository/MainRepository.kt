package com.dvt.weatherapp.data.repository

import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.dvt.weatherapp.data.api.helper.IApiHelper
import com.dvt.weatherapp.data.response.ForecastResponse
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: IApiHelper
) {
    suspend fun getWeather(): Response<CurrentWeatherResponse> = apiHelper.getWeather()
    suspend fun getForecast(): Response<ForecastResponse> = apiHelper.getForecast()
}