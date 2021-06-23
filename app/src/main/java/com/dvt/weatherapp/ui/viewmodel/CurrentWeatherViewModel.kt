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
    private val repository: MainRepository
): AndroidViewModel(application) {

    private val _state = MutableLiveData<CurrentWeatherState>()
    val state: LiveData<CurrentWeatherState>
        get() = _state

    private val _currentWeather = MutableLiveData<CurrentWeatherResponse>()
    val currentWeather: LiveData<CurrentWeatherResponse>
        get() = _currentWeather

    private val _temperatureList = MutableLiveData<List<TemperatureModel>>()
    val temperatureList: LiveData<List<TemperatureModel>>
        get() = _temperatureList

    private val _forecastList = MutableLiveData<List<CurrentWeatherResponse>>()
    val forecastList: LiveData<List<CurrentWeatherResponse>>
        get() = _forecastList

    init {
        fetchWeather()
    }

    private fun fetchWeather() = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(CurrentWeatherState.LOADING)

        val result = repository.getWeather()

        if (result.isSuccessful) {
            val data = result.body()
            val currentWeather = data!!
            _currentWeather.postValue(currentWeather)
            _temperatureList.postValue(buildTemperatureList(currentWeather.main))
            fetchForecast()
        } else {
            _state.postValue(CurrentWeatherState.ERROR)
        }
    }

    private fun fetchForecast() = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.getForecast()

        if (result.isSuccessful) {
            val data = result.body()!!
            val uniqueWeekDays = data.list
                .distinctBy { DateTimeUtil.format(it.dateTimeUnix, DateFormat.DAY_IN_FULL) }
                .filter { DateTimeUtil.isDayInTheFuture(it.dateTimeUnix) }
            _forecastList.postValue(uniqueWeekDays)
            _state.postValue(CurrentWeatherState.SUCCESS)
        } else {
            _state.postValue(CurrentWeatherState.ERROR)
        }
    }

    private fun buildTemperatureList(weatherMain: WeatherMain): List<TemperatureModel> {
        return listOf(
            TemperatureModel(
                title = getString(R.string.min),
                degree = weatherMain.temperatureMin
            ),
            TemperatureModel(
                title = getString(R.string.current),
                degree = weatherMain.temperature
            ),
            TemperatureModel(
                title = getString(R.string.max),
                degree = weatherMain.temperatureMax
            )
        )
    }

    private fun getString(@StringRes stringRes: Int): String {
        return getApplication<Application>().getString(stringRes)
    }

    fun onRetryClicked() {
        fetchWeather()
    }
}