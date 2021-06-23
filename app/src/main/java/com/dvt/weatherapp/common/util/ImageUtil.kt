package com.dvt.weatherapp.common.util

import com.dvt.weatherapp.R
import com.dvt.weatherapp.common.enums.WeatherCondition
import com.dvt.weatherapp.common.enums.WeatherCondition.Companion.toWeatherCondition
import com.dvt.weatherapp.common.model.Weather

class ImageUtil {
    companion object {
        fun getBackgroundImageFromWeather(weatherList: List<Weather>): Int? {
            if (weatherList.isEmpty()) return null

            val weather = weatherList.first()
            val weatherCondition = weather.id.toWeatherCondition() ?: return null

            return when (weatherCondition) {
                WeatherCondition.THUNDERSTORM,
                WeatherCondition.DRIZZLE,
                WeatherCondition.RAIN,
                WeatherCondition.SNOW -> R.drawable.forest_rainy
                WeatherCondition.ATMOSPHERE,
                WeatherCondition.CLEAR -> R.drawable.forest_sunny
                WeatherCondition.CLOUDS -> R.drawable.forest_cloudy
            }
        }
    }
}