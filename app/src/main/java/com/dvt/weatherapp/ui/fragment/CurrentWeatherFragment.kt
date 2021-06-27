package com.dvt.weatherapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.dvt.weatherapp.databinding.CurrentWeatherBinding
import com.dvt.weatherapp.ui.viewmodel.CurrentWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_current_weather.*

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {

    private val viewModel by viewModels<CurrentWeatherViewModel>()

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
        viewModel.apply {
            weatherWithForecastModel.observe(viewLifecycleOwner) {
                displayCurrentWeatherCondition(it.weather)
                view_weather.setWeatherWithForecastModel(it)
            }
        }
    }

    private fun displayCurrentWeatherCondition(currentWeather: CurrentWeatherResponse) {
        currentWeather.getWeatherConditionCluster()?.let {
            text_weather_condition.text = it.name
            it.backgroundImage?.let { image -> image_weather.setImageResource(image) }
        }
    }

    private fun setClickListeners() {
        view_error.onRetryClicked { viewModel.onRetryClicked() }
    }
}