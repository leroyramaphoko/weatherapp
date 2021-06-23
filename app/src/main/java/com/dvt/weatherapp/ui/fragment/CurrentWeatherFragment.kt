package com.dvt.weatherapp.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.dvt.weatherapp.R
import com.dvt.weatherapp.common.enums.WeatherConditionCluster.Companion.toWeatherConditionCluster
import com.dvt.weatherapp.databinding.CurrentWeatherBinding
import com.dvt.weatherapp.ui.adapter.ForecastAdapter
import com.dvt.weatherapp.ui.adapter.TemperatureAdapter
import com.dvt.weatherapp.ui.viewmodel.CurrentWeatherViewModel
import com.google.android.flexbox.*
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun setAdapters() {
        val recyclerViewTemperature = view?.findViewById<RecyclerView>(R.id.recycler_view_temperature)
        val recyclerViewForecast = view?.findViewById<RecyclerView>(R.id.recycler_view_forecast)
        temperatureAdapter = TemperatureAdapter()
        val layoutManager = FlexboxLayoutManager(requireContext(), FlexDirection.ROW)
        layoutManager.justifyContent = JustifyContent.SPACE_BETWEEN
        recyclerViewTemperature?.layoutManager = layoutManager
        recyclerViewTemperature?.adapter = temperatureAdapter

        forecastAdapter = ForecastAdapter()
        recyclerViewForecast?.adapter = forecastAdapter
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
                val imageWeather = view?.findViewById<ImageView>(R.id.image_weather)
                val weatherCondition = view?.findViewById<TextView>(R.id.text_weather_condition)
                it.weather.firstOrNull()?.let { weather ->
                    val weatherConditionCluster = weather.id.toWeatherConditionCluster()
                    val backgroundImage = weatherConditionCluster.backgroundImage
                    weatherCondition?.text = weatherConditionCluster.name
                    backgroundImage?.let { image -> imageWeather?.setImageResource(image) }
                    val color = weatherConditionCluster.colorRes
                    color?.let {
                        requireActivity().actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), color)))
                    }

                }
            }
        }
    }
}