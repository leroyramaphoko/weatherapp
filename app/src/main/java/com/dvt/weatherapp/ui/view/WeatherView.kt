package com.dvt.weatherapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.dvt.weatherapp.R
import com.dvt.weatherapp.common.model.TemperatureModel
import com.dvt.weatherapp.common.model.WeatherMain
import com.dvt.weatherapp.common.model.WeatherWithForecastModel
import com.dvt.weatherapp.databinding.WeatherViewBinding
import com.dvt.weatherapp.ui.adapter.ForecastAdapter
import com.dvt.weatherapp.ui.adapter.TemperatureAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.fragment_current_weather.*

class WeatherView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: WeatherViewBinding

    init {
        initView()
    }

    private fun initView() {
        val inflater = LayoutInflater.from(context)
        binding = WeatherViewBinding.inflate(inflater, this, true)
    }

    fun setWeatherWithForecastModel(weatherWithForecastModel: WeatherWithForecastModel) {
        setTemperatureAdapter(weatherWithForecastModel.weather.main)
        setForecastAdapter(weatherWithForecastModel)

        weatherWithForecastModel.weather.getWeatherConditionCluster()?.let {
            it.colorRes?.let { colorRes -> binding.container.setBackgroundColor(ContextCompat.getColor(context, colorRes)) }
        }
    }

    private fun setTemperatureAdapter(main: WeatherMain) {
        val adapter = TemperatureAdapter()

        val layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW)
        layoutManager.justifyContent = JustifyContent.SPACE_BETWEEN

        binding.recyclerViewWeather.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
        }

        adapter.submitList(buildTemperatureList(main))
    }

    private fun setForecastAdapter(weatherWithForecastModel: WeatherWithForecastModel) {
        val adapter = ForecastAdapter()
        binding.recyclerViewForecast.adapter = adapter
        adapter.submitList(weatherWithForecastModel.forecastList)
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
        return context.getString(stringRes)
    }
}