package com.dvt.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.weatherapp.data.repository.ForecastRepository
import com.dvt.weatherapp.data.repository.WeatherRepository
import com.dvt.weatherapp.data.response.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val forecastRepository: ForecastRepository
): ViewModel() {

    val favoriteLocationsWeather: LiveData<List<WeatherResponse>> = weatherRepository.getFavoriteWeatherConditions()

    fun clearWeatherLocations() = viewModelScope.launch(Dispatchers.IO) {
        weatherRepository.clearAll()
        forecastRepository.clearAll()
    }

}