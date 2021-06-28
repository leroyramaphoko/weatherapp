package com.dvt.weatherapp.ui.fragment

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.dvt.weatherapp.R
import com.dvt.weatherapp.common.constant.AppConstants
import com.dvt.weatherapp.common.extension.showIf
import com.dvt.weatherapp.common.util.ImageUtil
import com.dvt.weatherapp.data.response.WeatherResponse
import com.dvt.weatherapp.ui.adapter.FavoriteLocationWeatherAdapter
import com.dvt.weatherapp.ui.viewmodel.ExploreViewModel
import com.dvt.weatherapp.ui.viewmodel.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_explore.*

@AndroidEntryPoint
class ExploreFragment : Fragment(), GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    private lateinit var recyclerViewFavoriteLocationWeather: RecyclerView
    private var fabClearAll: View? = null
    private var currentLocationMarker: MarkerOptions? = null
    private lateinit var headerView: View
    private lateinit var adapter: FavoriteLocationWeatherAdapter
    private var lastKnownLocation: Location? = null
    private var locationPermissionGranted: Boolean = false
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var map: GoogleMap? = null
    private var currentLocation: LatLng? = null
    private val viewModel by viewModels<ExploreViewModel>()
    private val sharedViewModel by activityViewModels<MainViewModel>()

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        setMapEventListeners()
        updateLocationUI()
        getDeviceLocation()
    }

    private fun setMapEventListeners() {
        map?.apply {
            map?.setOnMapClickListener(this@ExploreFragment)
            map?.setOnMarkerClickListener(this@ExploreFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headerView = nav_view.getHeaderView(0)
        recyclerViewFavoriteLocationWeather = headerView.findViewById(R.id.recycler_view_favorite_location_weather)
        fabClearAll = headerView.findViewById(R.id.fab_clear_all)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        setAdapters()
        setClickListeners()
        setObservers()
    }

    private fun setAdapters() {
        adapter = FavoriteLocationWeatherAdapter(::onFavoriteLocationWeatherSelected)
        recyclerViewFavoriteLocationWeather.adapter = adapter
    }

    private fun onFavoriteLocationWeatherSelected(currentWeatherResponse: WeatherResponse) {
        moveCameraToLocation(
            LatLng(
                currentWeatherResponse.coordinate.latitude,
                currentWeatherResponse.coordinate.longitude
            )
        )

        closeFavoriteList()
    }

    private fun setObservers() {
        viewModel.favoriteLocationsWeather.observe(viewLifecycleOwner) {
            fabClearAll?.showIf(it.isNotEmpty())
            headerView.findViewById<View>(R.id.view_no_weather_locations).showIf(it.isEmpty())

            handlePaintMarkers(it)
        }
    }

    private fun handlePaintMarkers(favoriteWeatherList: List<WeatherResponse>) {
        map?.clear()
        currentLocationMarker?.let { marker -> map?.addMarker(marker) }
        adapter.submitList(favoriteWeatherList)

        favoriteWeatherList.forEach { weather ->
            createLocationMarker(
                weather.coordinate.latitude,
                weather.coordinate.longitude,
            )
        }
    }

    private fun setClickListeners() {
        fab_current_location.setOnClickListener {
            currentLocation?.let { moveCameraToLocation(it) }
        }

        headerView.apply {
            findViewById<View>(R.id.icon_close_favorite_list).setOnClickListener {
                closeFavoriteList()
            }

            fabClearAll?.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setMessage(R.string.confirm_clearing_weather_locations)
                    .setNegativeButton(R.string.cancel) { _, _ -> }
                    .setPositiveButton(R.string.ok) { _, _ ->
                        viewModel.clearWeatherLocations()
                    }
                    .show()
            }
        }
    }

    private fun updateLocationUI() {
        map?.let {
            try {
                if (locationPermissionGranted) {
                    it.isMyLocationEnabled = true
                    it.uiSettings.isMyLocationButtonEnabled = true
                } else {
                    it.isMyLocationEnabled = false
                    it.uiSettings.isMyLocationButtonEnabled = false
                    lastKnownLocation = null
                }
            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message, e)
            }
        }
    }

    private fun getDeviceLocation() {
        val deviceLocation = sharedViewModel.currentLocation.value

        if (deviceLocation == null) {
            val location = AppConstants.DEFAULT_LOCATION
            currentLocation = location
            addMarker(location)
            moveCameraToLocation(location)
            map?.uiSettings?.isMyLocationButtonEnabled = false
        } else {
            addMarker(deviceLocation)
            currentLocation = deviceLocation
            moveCameraToLocation(deviceLocation)
        }
    }

    private fun moveCameraToLocation(location: LatLng) {
        map?.animateCamera(
            CameraUpdateFactory
                .newLatLngZoom(
                    location,
                    AppConstants.DEFAULT_ZOOM_LEVEL.toFloat()
                )
        )
    }

    private fun addMarker(location: LatLng) {
        currentLocationMarker = MarkerOptions()
            .position(location)
        currentLocationMarker?.let {
            map?.addMarker(it)
        }
    }

    private fun createLocationMarker(
        latitude: Double,
        longitude: Double,
    ): Marker? {
        return map?.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .icon(ImageUtil.bitmapDescriptorFromVector(requireContext(), R.drawable.ic_favorite_location))
        )
    }

    override fun onMapClick(latLong: LatLng) {
        WeatherDetailsFragment.newInstance(latLong)
            .show(childFragmentManager)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        onMapClick(marker.position)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favourite_list) {
            openFavoriteList()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.explore_menu_top, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun openFavoriteList() {
        drawer_layout.openDrawer(GravityCompat.END)
    }

    private fun closeFavoriteList() {
        drawer_layout.closeDrawer(GravityCompat.END)
    }
}