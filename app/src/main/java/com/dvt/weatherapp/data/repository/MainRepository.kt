package com.dvt.weatherapp.data.repository

import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.dvt.weatherapp.data.api.helper.IApiHelper
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: IApiHelper
) {
    suspend fun getWeather(): Response<CurrentWeatherResponse> {
        return apiHelper.getWeather()
    }
}