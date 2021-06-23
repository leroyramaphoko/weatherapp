package com.dvt.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvt.weatherapp.common.model.TemperatureModel
import com.dvt.weatherapp.databinding.TemperatureBinding

class TemperatureAdapter: ListAdapter<TemperatureModel, TemperatureAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = inflater?.let { TemperatureBinding.inflate(it, parent, false) }

        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launchDetail = getItem(position)

        holder.bind(launchDetail)
    }

    private class DiffCallback : DiffUtil.ItemCallback<TemperatureModel>() {

        override fun areItemsTheSame(oldItem: TemperatureModel, newItem: TemperatureModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TemperatureModel, newItem: TemperatureModel): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(private val binding: TemperatureBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(temperatureModel: TemperatureModel) {
            binding.temperature = temperatureModel
        }
    }
}