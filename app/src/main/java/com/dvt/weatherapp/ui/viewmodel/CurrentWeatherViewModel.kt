package com.dvt.weatherapp.ui.viewmodel

import com.dvt.weatherapp.data.repository.ForecastRepository
import com.dvt.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    weatherRepository: WeatherRepository,
    forecastRepository: ForecastRepository
): BaseWeatherViewModel(weatherRepository, forecastRepository)