package com.dvt.weatherapp.data.api.service

import com.dvt.weatherapp.BuildConfig
import com.dvt.weatherapp.common.constant.AppConstants
import com.dvt.weatherapp.data.response.WeatherResponse
import com.dvt.weatherapp.data.response.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getWeather(
        @Query(AppConstants.LAT) latitude: Double,
        @Query(AppConstants.LON) longitude: Double,
        @Query(AppConstants.APP_ID) appId: String = BuildConfig.WEATHER_API_KEY,
        @Query(AppConstants.UNITS) units: String = AppConstants.PREFERRED_TEMPERATURE_UNIT.unit
    ): Response<WeatherResponse>

    @GET("forecast")
    suspend fun getForecast(
        @Query(AppConstants.LAT) latitude: Double,
        @Query(AppConstants.LON) longitude: Double,
        @Query(AppConstants.APP_ID) appId: String = BuildConfig.WEATHER_API_KEY,
        @Query(AppConstants.UNITS) units: String = AppConstants.PREFERRED_TEMPERATURE_UNIT.unit
    ):Response<ForecastResponse>
}