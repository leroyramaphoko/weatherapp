package com.dvt.weatherapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.dvt.weatherapp.data.response.WeatherResponse
import com.dvt.weatherapp.databinding.CurrentWeatherBinding
import com.dvt.weatherapp.ui.viewmodel.CurrentWeatherViewModel
import com.dvt.weatherapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_current_weather.*

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {

    private val viewModel by viewModels<CurrentWeatherViewModel>()
    private val sharedViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CurrentWeatherBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()

        setClickListeners()
    }

    private fun setObservers() {
        sharedViewModel.currentLocation.observe(viewLifecycleOwner) {
            viewModel.fetchWeather(it.latitude, it.longitude)
        }

        viewModel.apply {
            weatherResponse.observe(viewLifecycleOwner) {
                displayCurrentWeatherCondition(it)
                view_weather.setWeather(it)
            }

            forecastResponse.observe(viewLifecycleOwner) {
                view_weather.setForecast(it)
            }
        }
    }

    private fun displayCurrentWeatherCondition(currentWeather: WeatherResponse) {
        currentWeather.getWeatherConditionCluster()?.let {
            text_weather_condition.text = it.name
            it.colorRes?.let { colorRes -> container.setBackgroundColor(ContextCompat.getColor(requireContext(), colorRes)) }
            it.backgroundImage?.let { image -> image_weather.setImageResource(image) }
        }
    }

    private fun setClickListeners() {
        view_error.onRetryClicked { viewModel.onRetryClicked() }
    }
}