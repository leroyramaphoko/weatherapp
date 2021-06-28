package com.dvt.weatherapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.dvt.weatherapp.data.repository.ForecastRepository
import com.dvt.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val forecastRepository: ForecastRepository
): BaseWeatherViewModel(weatherRepository, forecastRepository) {

    fun onFabFavoriteLocationClicked() = viewModelScope.launch(Dispatchers.IO) {
        val weatherResponse = weatherResponse.value ?: return@launch
        val forecastResponse = forecastResponse.value ?: return@launch

        if (weatherResponse.favorite) {
            weatherResponse.favorite = false
            weatherRepository.delete(weatherResponse)
            forecastRepository.delete(forecastResponse)
        } else {
            weatherResponse.favorite = true
            weatherRepository.insert(weatherResponse)
            forecastRepository.insert(forecastResponse)
        }

        setFavoriteControl(weatherResponse.favorite)
    }

}