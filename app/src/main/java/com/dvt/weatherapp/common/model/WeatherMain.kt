package com.dvt.weatherapp.common.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class WeatherMain(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @SerializedName("temp") var temperature: Double,
    @SerializedName("temp_min") var temperatureMin: Double,
    @SerializedName("temp_max") var temperatureMax: Double
) {
    constructor() : this(0, 0.00, 0.00, 0.00)
}
