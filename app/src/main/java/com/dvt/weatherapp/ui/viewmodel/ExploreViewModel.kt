package com.dvt.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.weatherapp.data.repository.WeatherRepository
import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: WeatherRepository
): ViewModel() {
    val favoriteLocationsWeather: LiveData<List<CurrentWeatherResponse>> = repository.getFavoriteWeatherConditions()


}