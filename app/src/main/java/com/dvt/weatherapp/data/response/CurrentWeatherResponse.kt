package com.dvt.weatherapp.data.response

import androidx.room.*
import com.dvt.weatherapp.common.enums.WeatherConditionCluster
import com.dvt.weatherapp.common.converter.DataConverter
import com.dvt.weatherapp.common.model.Coordinate
import com.dvt.weatherapp.common.model.Weather
import com.dvt.weatherapp.common.model.WeatherMain
import com.google.gson.annotations.SerializedName

@Entity
data class CurrentWeatherResponse(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @Embedded(prefix = "coord") @SerializedName("coord") var coordinate: Coordinate,
    @SerializedName("dt") var dateTimeUnix: Long,
    @TypeConverters(DataConverter::class) @ColumnInfo(name = "weatherList") @SerializedName("weather") var weather: List<Weather>,
    @Embedded(prefix = "main") @SerializedName("main") var main: WeatherMain,
    var favorite: Boolean,
    var lastUpdated: Long
) {
    constructor(): this(0, Coordinate(), 0L, emptyList(), WeatherMain(), false, 0L)

    @Ignore fun getWeatherConditionCluster(): WeatherConditionCluster? {
        if (weather.isNullOrEmpty()) return null

        return WeatherConditionCluster.from(weather.first().id)
    }
}