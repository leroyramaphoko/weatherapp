package com.dvt.weatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dvt.weatherapp.common.converter.DataConverter
import com.dvt.weatherapp.common.model.Coordinate
import com.dvt.weatherapp.common.model.Weather
import com.dvt.weatherapp.common.model.WeatherMain
import com.dvt.weatherapp.data.db.dao.WeatherDao
import com.dvt.weatherapp.data.response.CurrentWeatherResponse

@Database(entities = [
    Weather::class,
    WeatherMain::class,
    CurrentWeatherResponse::class,
    Coordinate::class
], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}