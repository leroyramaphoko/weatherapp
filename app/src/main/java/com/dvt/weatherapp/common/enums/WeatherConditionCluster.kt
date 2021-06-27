package com.dvt.weatherapp.common.enums

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.dvt.weatherapp.R

enum class WeatherConditionCluster(@DrawableRes val backgroundImage: Int? = null,
                                   @ColorRes val colorRes: Int? = null,
                                   vararg val weatherConditions: WeatherCondition) {
    RAINY(
        R.drawable.forest_rainy,
        R.color.rainy,
        WeatherCondition.THUNDERSTORM,
        WeatherCondition.DRIZZLE,
        WeatherCondition.SNOW
    ),
    SUNNY(
        R.drawable.forest_sunny,
        R.color.rainy,
        WeatherCondition.ATMOSPHERE,
        WeatherCondition.CLEAR
    ),
    CLOUDY(
        R.color.cloudy,
        R.drawable.forest_cloudy,
        WeatherCondition.CLOUDS
    ),
    UNKNOWN;

    companion object {
        fun from(weatherId: Int): WeatherConditionCluster {
            val weatherCondition = WeatherCondition.from(weatherId)
            values().forEach {
                if (it.weatherConditions.contains(weatherCondition)) {
                    return it
                }
            }

            return UNKNOWN
        }
    }
}