package com.dvt.weatherapp.common.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(
    @ColumnInfo(name = "weatherId") @PrimaryKey var id: Int = 0,
    @ColumnInfo(name = "weatherMain") var main: String = "",
    @ColumnInfo(name = "weatherDescription") var description: String = "",
    @ColumnInfo(name = "weatherIcon") var icon: String = ""
)
