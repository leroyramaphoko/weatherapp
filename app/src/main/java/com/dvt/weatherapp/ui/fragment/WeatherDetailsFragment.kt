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
import com.dvt.weatherapp.ui.viewmodel.WeatherDetailsViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_weather_details.*

@AndroidEntryPoint
class WeatherDetailsFragment : BottomSheetDialogFragment() {
    private var latLng: LatLng? = null
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
            latLng = bundle.getParcelable(LAT_LON)
            displayLatLng(latLng)
        }

        setObservers()

        setClickListeners()
    }

    private fun displayLatLng(latLng: LatLng?) {
        latLng?.let {
            viewModel.fetchWeather(it.latitude, it.longitude)
            text_location_lat_lon.text = getString(
                R.string.lat_lon,
                LatLonUtil.format(it.latitude),
                LatLonUtil.format(it.longitude)
            )
        }
    }

    private fun setClickListeners() {
        fab_favorite_location.setOnClickListener {
            viewModel.onFabFavoriteLocationClicked()
        }

        view_error.onRetryClicked {
            viewModel.onRetryClicked()
        }
    }

    private fun setObservers() {
        viewModel.apply {
            weatherResponse.observe(viewLifecycleOwner) {
                view_weather.setWeather(it)
            }

            forecastResponse.observe(viewLifecycleOwner) {
                view_weather.setForecast(it)
            }

            favoriteControl.observe(viewLifecycleOwner) {
                if (it) {
                    fab_favorite_location.setImageResource(R.drawable.ic_favorite)
                } else {
                    fab_favorite_location.setImageResource(R.drawable.ic_favorite_unshaded)
                }
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
            arguments = bundleOf(LAT_LON to LatLonUtil.limitDecimalsOnLatLng(latLong))
        }
    }
}