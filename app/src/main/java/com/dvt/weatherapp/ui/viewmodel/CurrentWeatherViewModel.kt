package com.dvt.weatherapp.ui.viewmodel

import com.dvt.weatherapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    repository: MainRepository
): BaseWeatherViewModel(repository) {

}