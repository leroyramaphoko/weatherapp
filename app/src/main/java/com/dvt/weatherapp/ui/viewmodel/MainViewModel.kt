package com.dvt.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvt.weatherapp.common.util.LatLonUtil
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _currentLocation = MutableLiveData<LatLng>()
    val currentLocation: LiveData<LatLng>
        get() = _currentLocation

    fun setCurrentLocation(latLng: LatLng) {
        val formattedLatLng = LatLng(
            LatLonUtil.round(latLng.latitude),
            LatLonUtil.round(latLng.longitude),
        )
        _currentLocation.value = formattedLatLng
    }
}