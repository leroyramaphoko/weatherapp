package com.dvt.weatherapp.ui.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dvt.weatherapp.common.enums.WeatherConditionCluster.Companion.toWeatherConditionCluster
import com.dvt.weatherapp.databinding.CurrentWeatherBinding
import com.dvt.weatherapp.ui.adapter.ForecastAdapter
import com.dvt.weatherapp.ui.adapter.TemperatureAdapter
import com.dvt.weatherapp.ui.viewmodel.CurrentWeatherViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_current_weather.*

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {

    private lateinit var temperatureAdapter: TemperatureAdapter
    private lateinit var forecastAdapter: ForecastAdapter

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

        setAdapters()

        setObservers()

        setClickListeners()
    }

    private fun setAdapters() {
        temperatureAdapter = TemperatureAdapter()
        val layoutManager = FlexboxLayoutManager(requireContext(), FlexDirection.ROW)
        layoutManager.justifyContent = JustifyContent.SPACE_BETWEEN

        recycler_view_temperature.apply {
            this.layoutManager = layoutManager
            adapter = temperatureAdapter
        }

        forecastAdapter = ForecastAdapter()
        recycler_view_forecast.adapter = forecastAdapter
    }

    private fun setObservers() {
        viewModel.apply {
            temperatureList.observe(viewLifecycleOwner) {
                temperatureAdapter.submitList(it)
            }

            forecastList.observe(viewLifecycleOwner) {
                forecastAdapter.submitList(it)
            }

            currentWeather.observe(viewLifecycleOwner) {
                it.weather.firstOrNull()?.let { weather ->
                    val weatherConditionCluster = weather.id.toWeatherConditionCluster()
                    val backgroundImage = weatherConditionCluster.backgroundImage
                    text_weather_condition.text = weatherConditionCluster.name
                    backgroundImage?.let { image -> image_weather.setImageResource(image) }
                    val color = weatherConditionCluster.colorRes
                    color?.let {
                        requireActivity().actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), color)))
                    }

                }
            }
        }
    }

    private fun setClickListeners() {
        view_error.onRetryClicked { viewModel.onRetryClicked() }
    }
}