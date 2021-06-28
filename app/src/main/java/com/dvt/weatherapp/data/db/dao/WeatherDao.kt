package com.dvt.weatherapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvt.weatherapp.data.response.CurrentWeatherResponse

@Dao
interface WeatherDao {

    @Query("SELECT * FROM CurrentWeatherResponse ORDER BY lastUpdated")
    fun getAll(): LiveData<List<CurrentWeatherResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currentWeatherResponse: CurrentWeatherResponse)

    @Query("DELETE FROM CurrentWeatherResponse WHERE coordlatitude = :latitude AND coordlongitude = :longitude")
    suspend fun delete(latitude: Double?, longitude: Double?)

    @Query("SELECT * FROM CurrentWeatherResponse WHERE coordlatitude = :latitude AND coordlongitude = :longitude")
    suspend fun findByLatLon(latitude: Double?, longitude: Double?): CurrentWeatherResponse?

    @Query("DELETE FROM CurrentWeatherResponse")
    suspend fun clearAll()
}