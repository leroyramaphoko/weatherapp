package com.dvt.weatherapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvt.weatherapp.data.response.ForecastResponse

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecast: ForecastResponse)

    @Query("DELETE FROM ForecastResponse WHERE citycoordlatitude = :latitude AND citycoordlongitude = :longitude")
    suspend fun delete(latitude: Double?, longitude: Double?)

    @Query("SELECT * FROM ForecastResponse WHERE citycoordlatitude = :latitude AND citycoordlongitude = :longitude")
    suspend fun findByLatLon(latitude: Double?, longitude: Double?): ForecastResponse?

    @Query("DELETE FROM ForecastResponse")
    suspend fun clearAll()
}