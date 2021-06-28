package com.dvt.weatherapp.ui.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.dvt.weatherapp.R
import com.dvt.weatherapp.common.constant.AppConstants
import com.dvt.weatherapp.common.extension.showIf
import com.dvt.weatherapp.data.response.CurrentWeatherResponse
import com.dvt.weatherapp.ui.adapter.FavoriteLocationWeatherAdapter
import com.dvt.weatherapp.ui.viewmodel.ExploreViewModel
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

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map?.setOnMapClickListener(this)
        map?.setOnMarkerClickListener(this)
        updateLocationUI()
        getDeviceLocation()
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
        setAdapters()
        setClickListeners()
        setObservers()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
            requireActivity()
        )
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setAdapters() {
        adapter = FavoriteLocationWeatherAdapter(::onFavoriteLocationWeatherSelected)

        val recyclerViewFavoriteLocationWeather = headerView.findViewById<RecyclerView>(R.id.recycler_view_favorite_location_weather)
        recyclerViewFavoriteLocationWeather.adapter = adapter
    }

    private fun onFavoriteLocationWeatherSelected(currentWeatherResponse: CurrentWeatherResponse) {
        val location = LatLng(
            currentWeatherResponse.coordinate.latitude,
            currentWeatherResponse.coordinate.longitude
        )
        moveCameraToLocation(location)
        closeFavoriteList()
    }

    private fun setObservers() {
        viewModel.favoriteLocationsWeather.observe(viewLifecycleOwner) {
            map?.clear()
            currentLocationMarker?.let { marker -> map?.addMarker(marker) }
            adapter.submitList(it)
            fabClearAll?.showIf(it.isNotEmpty())
            headerView.findViewById<View>(R.id.view_no_weather_locations).showIf(it.isEmpty())

            it.forEach { weather ->
                createLocationMarker(
                    weather.coordinate.latitude,
                    weather.coordinate.longitude,
                )
            }
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
            
            fabClearAll = findViewById(R.id.fab_clear_all)

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
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result

                        if (lastKnownLocation != null) {
                            val location = LatLng(
                                lastKnownLocation!!.latitude,
                                lastKnownLocation!!.longitude
                            )
                            addMarker(location)
                            currentLocation = location
                            moveCameraToLocation(location)
                        }
                    } else {
                        val location = AppConstants.DEFAULT_LOCATION
                        currentLocation = location
                        addMarker(location)
                        moveCameraToLocation(location)
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
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
                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_favorite_location))
        )
    }

    private fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorResId: Int
    ): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
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
        when (item.itemId) {
            R.id.favourite_list -> openFavoriteList()
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

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100
    }
}