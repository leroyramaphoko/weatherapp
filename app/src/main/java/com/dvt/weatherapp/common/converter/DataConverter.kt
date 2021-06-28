package com.dvt.weatherapp.common.converter

import androidx.room.TypeConverter
import com.dvt.weatherapp.common.model.Weather
import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
    @TypeConverter
    fun fromWeatherList(weather: List<Weather>): String? {
        val type = object : TypeToken<List<Weather>>() {}.type
        return Gson().toJson(weather, type)
    }

    @TypeConverter
    fun toWeatherList(weatherString: String?): List<Weather>? {
        if (weatherString == null) {
            return null
        }

        val type = object : TypeToken<List<Weather?>?>() {}.type
        return Gson().fromJson<List<Weather>>(weatherString, type)
    }

    @TypeConverter
    fun fromForecastList(forecastList: List<CurrentWeatherResponse>): String? {
        val type = object : TypeToken<List<CurrentWeatherResponse>>() {}.type
        return Gson().toJson(forecastList, type)
    }

    @TypeConverter
    fun toForecastList(forecastListString: String?): List<CurrentWeatherResponse>? {
        if (forecastListString == null) {
            return null
        }

        val type = object : TypeToken<List<CurrentWeatherResponse?>?>() {}.type
        return Gson().fromJson<List<CurrentWeatherResponse>>(forecastListString, type)
    }
}