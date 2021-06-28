package com.dvt.weatherapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvt.weatherapp.data.response.WeatherResponse

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherResponse ORDER BY lastUpdated")
    fun getAll(): LiveData<List<WeatherResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currentWeatherResponse: WeatherResponse)

    @Query("DELETE FROM WeatherResponse WHERE coordlatitude = :latitude AND coordlongitude = :longitude")
    suspend fun delete(latitude: Double?, longitude: Double?)

    @Query("SELECT * FROM WeatherResponse WHERE coordlatitude = :latitude AND coordlongitude = :longitude")
    suspend fun findByLatLon(latitude: Double?, longitude: Double?): WeatherResponse?

    @Query("DELETE FROM WeatherResponse")
    suspend fun clearAll()
}