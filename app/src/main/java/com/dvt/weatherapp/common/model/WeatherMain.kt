package com.dvt.weatherapp.common.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class WeatherMain(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("temp") var temperature: Double = 0.00,
    @SerializedName("temp_min") var temperatureMin: Double = 0.00,
    @SerializedName("temp_max") var temperatureMax: Double = 0.00
) {
    constructor() : this(0, 0.00, 0.00, 0.00)
}
