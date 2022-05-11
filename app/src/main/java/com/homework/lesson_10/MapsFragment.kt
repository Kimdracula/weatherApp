package com.homework.lesson_10

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.homework.weatherapp.R
import com.homework.weatherapp.databinding.FragmentMapsMainBinding
import com.homework.weatherapp.model.City
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.utils.KEY_BUNDLE_WEATHER
import com.homework.weatherapp.view.details.DetailsFragment
import java.util.*
import kotlin.collections.ArrayList

class MapsFragment : Fragment() {

    private var _binding: FragmentMapsMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private val markers: ArrayList<Marker> = arrayListOf()
    private var searchAddressText: String? = null
    private var searchResultLat: Double? = null
    private var searchResultLon: Double? = null

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val spb = LatLng(59.9342802, 30.335098600000038)
        permissionCheck()
        map.apply {
            addMarker(MarkerOptions().position(spb).title("Marker in Saint Petersburg"))
            moveCamera(CameraUpdateFactory.newLatLng(spb))
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
            setOnMapLongClickListener {
                addMarkerToArray(it)
                drawLine()
            }
        }
    }

    private fun permissionCheck() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                map.isMyLocationEnabled = true
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showAlertDialog(
                    getString(R.string.GPS_accsess),
                    getString(R.string.GPS_explanation)
                )
            }
            else -> {
                runPermissionRequest()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                map.isMyLocationEnabled = true
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                map.isMyLocationEnabled = true
            }
            else -> {
                showAlertDialog(
                    getString(R.string.GEO_accsess), getString(R.string.GEO_explanation)
                )
            }
        }
    }


    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(
            requireContext()
        )
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                runPermissionRequest()
            }
            .setNegativeButton(getString(R.string.no_alert_button)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    private fun runPermissionRequest() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun drawLine() {
        var previousBefore: Marker? = null
        markers.forEach { current ->
            previousBefore?.let { previous ->
                map.addPolyline(
                    PolylineOptions().add(previous.position, current.position)
                        .color(Color.RED)
                        .width(5f)
                )
            }
            previousBefore = current
        }
    }

    private fun setMarker(
        location: LatLng,
        searchText: String,
    ): Marker {
        return map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
        )!!
    }

    private fun addMarkerToArray(it: LatLng) {
        val marker = setMarker(it, markers.size.toString())
        markers.add(marker)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initButtons()
    }

    private fun initButtons() {
        binding.buttonSearch.setOnClickListener {
            searchAddress()
        }
        binding.buttonSearchWeather.setOnClickListener {
            searchWeather()
        }
    }

    private fun searchWeather() {
        requireActivity().supportFragmentManager.beginTransaction().add(
            R.id.fragment_container,
            DetailsFragment.newInstance(Bundle().apply {
                if (!searchAddressText.isNullOrEmpty() && searchResultLat != null && searchResultLon != null) {
                    putParcelable(
                        KEY_BUNDLE_WEATHER,
                        Weather(City(searchAddressText!!, searchResultLat!!, searchResultLon!!))
                    )
                }
            })
        )
            .addToBackStack("").commit()
    }

    private fun searchAddress() {
        try {
            searchAddressText = binding.searchAddress.text.trim().toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            val geocoder = Geocoder(requireContext())
            Thread {
                if (!searchAddressText.isNullOrEmpty()) {
                    val searchResult = geocoder.getFromLocationName(searchAddressText, 1)[0]
                    searchResultLat = searchResult.latitude
                    searchResultLon = searchResult.longitude
                }
            }.start()

            val newMarker = LatLng(
                searchResultLat!!,
                searchResultLon!!
            )
            with(map) {
                addMarker(MarkerOptions().position(newMarker).title("Marker in Saint Petersburg"))
                moveCamera(CameraUpdateFactory.newLatLngZoom(newMarker, 10f))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            showAlertDialog(getString(R.string.searchText_mistake), getString(R.string.check_text))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}