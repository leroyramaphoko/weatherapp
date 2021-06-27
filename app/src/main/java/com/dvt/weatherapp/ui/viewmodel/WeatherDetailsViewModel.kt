package com.dvt.weatherapp.ui.viewmodel

import com.dvt.weatherapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    repository: MainRepository
): BaseWeatherViewModel(repository) {
    fun onFabFavoriteLocationClicked() {
        val weatherWithForecastModel = weatherWithForecastModel.value ?: return
        weatherWithForecastModel.favorite = !weatherWithForecastModel.favorite

        setWeatherWithForecastModel(weatherWithForecastModel)
    }

}