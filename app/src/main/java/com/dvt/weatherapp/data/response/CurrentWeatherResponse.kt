package com.dvt.weatherapp.data.response

import androidx.room.*
import com.dvt.weatherapp.common.enums.WeatherConditionCluster
import com.dvt.weatherapp.common.converter.DataConverter
import com.dvt.weatherapp.common.model.Weather
import com.dvt.weatherapp.common.model.WeatherMain
import com.google.gson.annotations.SerializedName

@Entity
data class CurrentWeatherResponse(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @SerializedName("dt") var dateTimeUnix: Long,
    @TypeConverters(DataConverter::class) @ColumnInfo(name = "weatherList") @SerializedName("weather") var weather: List<Weather>,
    @Embedded(prefix = "main") @SerializedName("main") var main: WeatherMain
) {
    constructor(): this(0, 0L, emptyList(), WeatherMain())

    @Ignore fun getWeatherConditionCluster(): WeatherConditionCluster? {
        if (weather.isNullOrEmpty()) return null

        return WeatherConditionCluster.from(weather.first().id)
    }
}