package com.dvt.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvt.weatherapp.R
import com.dvt.weatherapp.common.enums.DateFormat
import com.dvt.weatherapp.common.util.DateTimeUtil
import com.dvt.weatherapp.common.util.LatLonUtil
import com.dvt.weatherapp.data.response.CurrentWeatherResponse

class FavoriteLocationWeatherAdapter(
    private val onItemSelected: (CurrentWeatherResponse) -> Unit
): ListAdapter<CurrentWeatherResponse, FavoriteLocationWeatherAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_favorite_location_weather, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentWeatherResponse = getItem(position)
        val context = holder.itemView.context

        holder.textLocation?.text = context.getString(
            R.string.lat_lon,
            LatLonUtil.format(currentWeatherResponse.coordinate.latitude),
            LatLonUtil.format(currentWeatherResponse.coordinate.longitude)
        )
        holder.textLastUpdated?.text = DateTimeUtil.format(currentWeatherResponse.lastUpdated, DateFormat.DD_MMM_YYYY_HH_MM_AA)

        holder.itemView.setOnClickListener {
            onItemSelected.invoke(getItem(holder.adapterPosition))
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<CurrentWeatherResponse>() {

        override fun areItemsTheSame(oldItem: CurrentWeatherResponse, newItem: CurrentWeatherResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CurrentWeatherResponse, newItem: CurrentWeatherResponse): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textLocation = view.findViewById(R.id.text_location) as TextView?
        val textLastUpdated = view.findViewById(R.id.text_last_updated) as TextView?
    }
}