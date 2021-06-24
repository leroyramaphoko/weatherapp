package com.dvt.weatherapp.ui.viewmodel

import android.app.Application
import com.dvt.weatherapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    application: Application,
    repository: MainRepository
): BaseWeatherViewModel(application, repository) {

}