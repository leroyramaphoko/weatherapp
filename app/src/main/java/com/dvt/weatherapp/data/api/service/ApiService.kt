package com.dvt.weatherapp.data.api.service

import com.dvt.weatherapp.BuildConfig
import com.dvt.weatherapp.common.constant.AppConstants
import com.dvt.weatherapp.common.constant.AppSettings
import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.dvt.weatherapp.data.response.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather?q=Johannesburg")
    suspend fun getWeather(
        @Query(AppConstants.APP_ID) appId: String = BuildConfig.WEATHER_API_KEY,
        @Query(AppConstants.UNITS) units: String = AppSettings.PREFERRED_TEMPERATURE_UNIT.unit
    ): Response<CurrentWeatherResponse>

    @GET("forecast?q=Johannesburg")
    suspend fun getForecast(
        @Query(AppConstants.APP_ID) appId: String = BuildConfig.WEATHER_API_KEY,
        @Query(AppConstants.UNITS) units: String = AppSettings.PREFERRED_TEMPERATURE_UNIT.unit
    ):Response<ForecastResponse>
}