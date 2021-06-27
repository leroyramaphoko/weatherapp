package com.dvt.weatherapp.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.dvt.weatherapp.R
import com.dvt.weatherapp.common.util.LatLonUtil
import com.dvt.weatherapp.databinding.WeatherDetailsBinding
import com.dvt.weatherapp.ui.adapter.ForecastAdapter
import com.dvt.weatherapp.ui.adapter.TemperatureAdapter
import com.dvt.weatherapp.ui.viewmodel.WeatherDetailsViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_weather_details.*

@AndroidEntryPoint
class WeatherDetailsFragment : BottomSheetDialogFragment() {
    private var latLong: LatLng? = null
    private val viewModel by viewModels<WeatherDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = WeatherDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            latLong = bundle.getParcelable(LAT_LON)
            latLong?.let {
                viewModel.fetchWeather(it.latitude, it.longitude)

                text_location_lat_lon.text = getString(
                    R.string.lat_lon,
                    LatLonUtil.format(it.latitude),
                    LatLonUtil.format(it.longitude)
                )
            }
        }

        setObservers()
    }

    private fun setObservers() {
        viewModel.apply {
            weatherWithForecastModel.observe(viewLifecycleOwner) {
                view_weather.setWeatherWithForecastModel(it)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)
                val behaviour = BottomSheetBehavior.from(bottomSheet)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.skipCollapsed = true
            }
        }
    }

    fun show(manager: FragmentManager) {
        super.show(manager, TAG)
    }

    companion object {
        private const val LAT_LON = "latLon"
        private val TAG = WeatherDetailsFragment::class.java.simpleName

        fun newInstance(latLong: LatLng) = WeatherDetailsFragment().apply {
            arguments = bundleOf(LAT_LON to latLong)
        }
    }
}