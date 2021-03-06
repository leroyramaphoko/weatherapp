package com.dvt.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvt.weatherapp.common.enums.DateFormat
import com.dvt.weatherapp.common.util.DateTimeUtil
import com.dvt.weatherapp.common.util.ImageUtil
import com.dvt.weatherapp.data.response.WeatherResponse
import com.dvt.weatherapp.databinding.ForecastBinding

class ForecastAdapter: ListAdapter<WeatherResponse, ForecastAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = inflater?.let { ForecastBinding.inflate(it, parent, false) }

        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherResponse = getItem(position)

        holder.textWeekDay.text = DateTimeUtil.format(weatherResponse.dateTimeUnix, DateFormat.DAY_IN_FULL)

        weatherResponse.weather.firstOrNull()?.let { weather ->
            ImageUtil.getForecastWeatherIcon(weather.id)?.let {
                holder.iconWeather.setImageResource(it)
            }
        }

        holder.bind(weatherResponse)
    }

    private class DiffCallback : DiffUtil.ItemCallback<WeatherResponse>() {

        override fun areItemsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(private val binding: ForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        val textWeekDay = binding.textWeekDay
        val iconWeather = binding.iconWeather

        fun bind(weather: WeatherResponse) {
            binding.weather = weather
        }
    }
}