package com.dvt.weatherapp.data.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dvt.weatherapp.common.converter.DataConverter
import com.dvt.weatherapp.common.model.City
import com.google.gson.annotations.SerializedName

@Entity
class ForecastResponse(
    @PrimaryKey(autoGenerate = true) var forecastId: Int,
    @Embedded(prefix = "city") @SerializedName("city") var city: City,
    @TypeConverters(DataConverter::class) val list: List<CurrentWeatherResponse>
) {
    constructor() : this(0, City(), emptyList())
}