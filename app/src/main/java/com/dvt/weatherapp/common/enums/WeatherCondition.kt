package com.dvt.weatherapp.common.enums

enum class WeatherCondition(val minId: Int, val maxId: Int) {
    THUNDERSTORM(200, 232),
    DRIZZLE(300, 321),
    RAIN(500, 531),
    SNOW(600, 622),
    ATMOSPHERE(701, 781),
    CLEAR(800, 800),
    CLOUDS(801, 804);

    companion object {
        fun Int.toWeatherCondition(): WeatherCondition? {
            values().forEach {
                if (it.minId >= this && it.maxId <= this) {
                    return it
                }
            }

            return null
        }
    }

}

