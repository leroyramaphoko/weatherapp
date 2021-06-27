package com.dvt.weatherapp.ui.viewmodel

import com.dvt.weatherapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    repository: MainRepository
): BaseWeatherViewModel(repository) {

    init {
        // TODO: Use the current lat lon
        fetchWeather(-26.125210, 27.931410)
    }

}