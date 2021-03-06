package com.dvt.weatherapp.common.enums

enum class WeatherCondition(val min: Int, val max: Int) {
    THUNDERSTORM(200, 232),
    DRIZZLE(300, 321),
    RAIN(500, 531),
    SNOW(600, 622),
    ATMOSPHERE(701, 781),
    CLEAR(800, 800),
    CLOUDS(801, 804);

    companion object {
        fun from(weatherId: Int): WeatherCondition? {
            values().forEach {
                if (weatherId in it.min..it.max) {
                    return it
                }
            }

            return null
        }
    }

}

