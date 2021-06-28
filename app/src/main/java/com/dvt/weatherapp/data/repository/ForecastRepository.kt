package com.dvt.weatherapp.data.repository

import com.dvt.weatherapp.common.util.DateTimeUtil
import com.dvt.weatherapp.data.api.helper.IApiHelper
import com.dvt.weatherapp.data.db.dao.ForecastDao
import com.dvt.weatherapp.data.response.ForecastResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val apiHelper: IApiHelper,
    private val forecastDao: ForecastDao) {

    suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Response<ForecastResponse> {

        forecastDao.findByLatLon(latitude, longitude)?.let {
            return Response.success(it)
        }

        return try {
            apiHelper.getForecast(latitude, longitude)
        } catch (e: Exception) {
            Response.error(400, getUnknownErrorResponse("Something went wrong"))
        }
    }

    suspend fun insert(forecast: ForecastResponse) {
        forecastDao.insert(forecast)
    }

    suspend fun delete(forecast: ForecastResponse) {
        forecastDao.delete(forecast.city.coordinate.latitude, forecast.city.coordinate.longitude)
    }

    suspend fun clearAll() {
        forecastDao.clearAll()
    }

    private fun getUnknownErrorResponse(message: String? = "Unknown"): ResponseBody {
        return ResponseBody.create(
            "application/json".toMediaTypeOrNull(),
            "{\"message\":[\"${message}\"]}"
        )
    }
}