package com.dvt.weatherapp.ui.viewmodel

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.dvt.weatherapp.R
import com.dvt.weatherapp.common.constant.AppSettings
import com.dvt.weatherapp.common.enums.DateFormat
import com.dvt.weatherapp.common.model.TemperatureModel
import com.dvt.weatherapp.common.model.WeatherMain
import com.dvt.weatherapp.common.util.DateTimeUtil
import com.dvt.weatherapp.data.repository.MainRepository
import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.dvt.weatherapp.data.response.ForecastResponse
import com.dvt.weatherapp.ui.enums.CurrentWeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    application: Application,
    repository: MainRepository
): BaseWeatherViewModel(application, repository) {

    init {
        // TODO: Use the current lat lon
        fetchWeather(-26.125210, 27.931410)
    }

}