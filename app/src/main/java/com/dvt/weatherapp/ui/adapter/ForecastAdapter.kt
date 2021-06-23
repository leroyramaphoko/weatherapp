package com.dvt.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvt.weatherapp.R
import com.dvt.weatherapp.common.enums.DateFormat
import com.dvt.weatherapp.common.enums.WeatherCondition
import com.dvt.weatherapp.common.enums.WeatherConditionCluster
import com.dvt.weatherapp.common.enums.WeatherConditionCluster.Companion.toWeatherConditionCluster
import com.dvt.weatherapp.common.util.DateTimeUtil
import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.dvt.weatherapp.databinding.ForecastBinding

class ForecastAdapter: ListAdapter<CurrentWeatherResponse, ForecastAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = inflater?.let { ForecastBinding.inflate(it, parent, false) }

        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = getItem(position)

        holder.textWeekDay.text = DateTimeUtil.format(weather.dateTimeUnix, DateFormat.DAY_IN_FULL)

        weather.weather.firstOrNull()?.let {
            val weatherConditionCluster = it.id.toWeatherConditionCluster()
//            weatherConditionCluster(weatherConditionCluster)
        }

        holder.bind(weather)
    }

//    private fun weatherConditionCluster(weatherConditionCluster: WeatherConditionCluster): Int {
//        return when (weatherConditionCluster) {
//            WeatherConditionCluster.RAINY -> R.drawable.rain
//            WeatherConditionCluster.SUNNY -> R.drawable.partlysunny
//            WeatherConditionCluster.CLOUDY -> R.drawable.partlysunny
//        }
//    }

    private class DiffCallback : DiffUtil.ItemCallback<CurrentWeatherResponse>() {

        override fun areItemsTheSame(oldItem: CurrentWeatherResponse, newItem: CurrentWeatherResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CurrentWeatherResponse, newItem: CurrentWeatherResponse): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(private val binding: ForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        val textWeekDay = binding.textWeekDay

        fun bind(weather: CurrentWeatherResponse) {
            binding.weather = weather
        }
    }
}