package com.dvt.weatherapp.common.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class City(
    @PrimaryKey @SerializedName("id") val cityId: Long,
    @Embedded(prefix = "coord") @SerializedName("coord") val coordinate: Coordinate
) {
    constructor() : this(0L, Coordinate())
}
