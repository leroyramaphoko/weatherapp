package com.dvt.weatherapp.common.converter

import androidx.room.TypeConverter
import com.dvt.weatherapp.common.model.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
    @TypeConverter
    fun fromWeatherList(countryLang: List<Weather>): String? {
        val type = object : TypeToken<List<Weather>>() {}.type
        return Gson().toJson(countryLang, type)
    }

    @TypeConverter
    fun toWeatherList(countryLangString: String?): List<Weather>? {
        if (countryLangString == null) {
            return null
        }

        val type = object : TypeToken<List<Weather?>?>() {}.type
        return Gson().fromJson<List<Weather>>(countryLangString, type)
    }
}