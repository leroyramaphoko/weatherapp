package com.dvt.weatherapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.dvt.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val repository: WeatherRepository
): BaseWeatherViewModel(repository) {

    fun onFabFavoriteLocationClicked() = viewModelScope.launch(Dispatchers.IO) {
        val weatherResponse = weatherResponse.value ?: return@launch

        if (weatherResponse.favorite) {
            weatherResponse.favorite = false
            repository.deleteWeather(weatherResponse)
        } else {
            weatherResponse.favorite = true
            repository.insertWeather(weatherResponse)
        }
        setFavoriteControl(weatherResponse.favorite)
    }

}