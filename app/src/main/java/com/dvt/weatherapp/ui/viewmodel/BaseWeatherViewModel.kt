package com.dvt.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.weatherapp.common.enums.DateFormat
import com.dvt.weatherapp.common.model.WeatherWithForecastModel
import com.dvt.weatherapp.common.util.DateTimeUtil
import com.dvt.weatherapp.data.repository.MainRepository
import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.dvt.weatherapp.ui.enums.WeatherState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseWeatherViewModel(
    private val repository: MainRepository
): ViewModel() {

    private val _state = MutableLiveData<WeatherState>()
    val state: LiveData<WeatherState>
        get() = _state

    private val _weatherWithForecastModel = MutableLiveData<WeatherWithForecastModel>()
    val weatherWithForecastModel: LiveData<WeatherWithForecastModel>
        get() = _weatherWithForecastModel

    private var _latitude: Double? = null
    private var _longitude: Double? = null

    fun fetchWeather(latitude: Double, longitude: Double) = viewModelScope.launch(Dispatchers.IO) {
        _latitude = latitude
        _longitude = longitude
        setState(WeatherState.LOADING)

        val result = repository.getWeather(latitude, longitude)

        if (result.isSuccessful) {
            val data = result.body()
            val currentWeather = data!!
            fetchForecast(latitude, longitude, currentWeather)
        } else {
            setState(WeatherState.ERROR)
        }
    }

    private fun fetchForecast(
        latitude: Double,
        longitude: Double,
        currentWeather: CurrentWeatherResponse
    ) = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.getForecast(latitude, longitude)

        if (result.isSuccessful) {
            val data = result.body()!!
            val forecastList = data.list
                .distinctBy { DateTimeUtil.format(it.dateTimeUnix, DateFormat.DAY_IN_FULL) }
                .filter { DateTimeUtil.isDayInTheFuture(it.dateTimeUnix) }

            val weatherWithForecastModel = WeatherWithForecastModel(
                weather = currentWeather,
                forecastList = forecastList
            )

            _weatherWithForecastModel.postValue(weatherWithForecastModel)
            setState(WeatherState.SUCCESS)
        } else {
            setState(WeatherState.ERROR)
        }
    }

    private fun setState(state: WeatherState) {
        _state.postValue(state)
    }

    fun onRetryClicked() {
        val latitude = _latitude ?: return
        val longitude = _longitude ?: return

        fetchWeather(latitude, longitude)
    }
}