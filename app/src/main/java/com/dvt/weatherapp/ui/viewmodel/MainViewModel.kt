package com.dvt.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.weatherapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {
    fun getWeather() = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.getWeather()

        if (result.isSuccessful) {
            val data = result.body()
            val tt = data
        }
    }
}