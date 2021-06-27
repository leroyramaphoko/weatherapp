package com.dvt.weatherapp.common.util

import com.dvt.weatherapp.R
import com.dvt.weatherapp.common.enums.WeatherCondition
import com.dvt.weatherapp.common.enums.WeatherCondition.*

class ImageUtil {
    companion object {
        fun getForecastWeatherIcon(weatherId: Int): Int? {
            val weatherCondition = WeatherCondition.from(weatherId) ?: return null

            return when (weatherCondition) {
                THUNDERSTORM, DRIZZLE, RAIN, SNOW -> R.drawable.rain
                ATMOSPHERE, CLOUDS -> R.drawable.partlysunny
                CLEAR -> R.drawable.clear
            }
        }
    }
}