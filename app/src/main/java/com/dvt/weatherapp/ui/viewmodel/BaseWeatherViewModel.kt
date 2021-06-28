package com.dvt.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.weatherapp.common.enums.DateFormat
import com.dvt.weatherapp.common.util.DateTimeUtil
import com.dvt.weatherapp.data.repository.ForecastRepository
import com.dvt.weatherapp.data.repository.WeatherRepository
import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.dvt.weatherapp.data.response.ForecastResponse
import com.dvt.weatherapp.ui.enums.WeatherState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseWeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val forecastRepository: ForecastRepository
): ViewModel() {

    private val _favoriteControl = MutableLiveData<Boolean>()
    val favoriteControl: LiveData<Boolean>
        get() = _favoriteControl

    private val _state = MutableLiveData<WeatherState>()
    val state: LiveData<WeatherState>
        get() = _state

    private val _weatherResponse = MutableLiveData<CurrentWeatherResponse>()
    val weatherResponse: LiveData<CurrentWeatherResponse>
        get() = _weatherResponse

    private val _forecastResponse = MutableLiveData<ForecastResponse>()
    val forecastResponse: LiveData<ForecastResponse>
        get() = _forecastResponse

    private var _latitude: Double? = null
    private var _longitude: Double? = null

    fun fetchWeather(latitude: Double, longitude: Double) = viewModelScope.launch(Dispatchers.IO) {
        _latitude = latitude
        _longitude = longitude
        setState(WeatherState.LOADING)

        val result = weatherRepository.getWeather(latitude, longitude)

        if (result.isSuccessful) {
            val data = result.body()
            val currentWeather = data!!

            _weatherResponse.postValue(currentWeather)
            setFavoriteControl(currentWeather.favorite)
            fetchForecast(latitude, longitude)
        } else {
            setState(WeatherState.ERROR)
        }
    }

    fun setFavoriteControl(favorite: Boolean) {
        _favoriteControl.postValue(favorite)
    }

    private fun fetchForecast(latitude: Double, longitude: Double) = viewModelScope.launch(Dispatchers.IO) {
        val result = forecastRepository.getForecast(latitude, longitude)

        if (result.isSuccessful) {
            val data = result.body()!!
            _forecastResponse.postValue(data)
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