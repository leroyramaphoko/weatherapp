package com.dvt.weatherapp.common.model

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)
