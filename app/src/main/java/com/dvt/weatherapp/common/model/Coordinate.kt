package com.dvt.weatherapp.common.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["latitude", "longitude"])
data class Coordinate(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double
) {
    constructor() : this(0.00, 0.00)
}
