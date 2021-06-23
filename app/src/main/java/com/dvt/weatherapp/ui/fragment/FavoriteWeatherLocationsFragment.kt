package com.dvt.weatherapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dvt.weatherapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteWeatherLocationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_weather_locations, container, false)
    }

}