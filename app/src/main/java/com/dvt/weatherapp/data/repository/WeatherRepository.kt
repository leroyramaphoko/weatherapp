package com.dvt.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.dvt.weatherapp.common.util.DateTimeUtil
import com.dvt.weatherapp.data.api.helper.IApiHelper
import com.dvt.weatherapp.data.db.dao.WeatherDao
import com.dvt.weatherapp.data.response.WeatherResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiHelper: IApiHelper,
    private val weatherDao: WeatherDao
) {
    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): Response<WeatherResponse> {

        weatherDao.findByLatLon(latitude, longitude)?.let { return Response.success(it) }

        return try {
            apiHelper.getWeather(latitude, longitude)
        } catch (e: Exception) {
            Response.error(400, getUnknownErrorResponse("Something went wrong"))
        }
    }

    suspend fun insert(weather: WeatherResponse) {
        weather.lastUpdated = DateTimeUtil.getCurrentTimeMills()
        weatherDao.insert(weather)
    }

    suspend fun delete(weather: WeatherResponse) {
        weatherDao.delete(weather.coordinate.latitude, weather.coordinate.longitude)
    }

    fun getFavoriteWeatherConditions(): LiveData<List<WeatherResponse>> {
        return weatherDao.getAll()
    }

    suspend fun clearAll() {
        weatherDao.clearAll()
    }

    private fun getUnknownErrorResponse(message: String? = "Unknown"): ResponseBody {
        return ResponseBody.create(
            "application/json".toMediaTypeOrNull(),
            "{\"message\":[\"${message}\"]}"
        )
    }
}