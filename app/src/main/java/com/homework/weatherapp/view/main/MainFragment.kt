package com.homework.weatherapp.view.main

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.homework.weatherapp.R
import com.homework.weatherapp.databinding.FragmentMainBinding
import com.homework.weatherapp.model.City
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.utils.KEY_BUNDLE_WEATHER
import com.homework.weatherapp.utils.SHARED_PREF_KEY
import com.homework.weatherapp.view.details.DetailsFragment
import com.homework.weatherapp.view_model.MainState
import com.homework.weatherapp.view_model.MainViewModel
import java.io.IOException


class MainFragment : Fragment(), OnItemListClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var isRussian = true
    private val adapter = MfAdapter(this)
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val observer = Observer<MainState> { renderData(it) }
        initViews(observer)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        if (sharedPref.getBoolean(SHARED_PREF_KEY, true)) {
            getRussia()
        } else {
            getWorld()
        }
        initDecorator()

    }

    private fun getRussia() {
        viewModel.getWeatherRussia()
        binding.fabRegion.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.russia_ic
            )
        )
    }

    private fun getWorld() {
        viewModel.getWeatherWorld()
        binding.fabRegion.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.earth_ic
            )
        )
    }


    private fun initViews(observer: Observer<MainState>) {

        with(binding) {
            with(viewModel) {
                recycleList.adapter = adapter
                getData().observe(viewLifecycleOwner, observer)
                fabRegion.setOnClickListener {
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                    isRussian = !isRussian
                    if (isRussian) {
                        getRussia()
                        if (sharedPref != null) {
                            with(sharedPref.edit())
                            {
                                putBoolean(SHARED_PREF_KEY, true)
                                apply()
                            }
                        }

                    } else {
                        getWorld()
                        with(sharedPref!!.edit())
                        {
                            putBoolean(SHARED_PREF_KEY, false)
                            apply()
                        }
                    }
                }
                fabLocation.setOnClickListener {
                    permissionCheck()
                }

            }
        }
    }

    private fun initDecorator() {
        val itemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator, null))
        binding.recycleList.addItemDecoration(itemDecoration)
    }

    private fun renderData(data: MainState) {
        with(binding) {
            when (data) {
                is MainState.Error -> {
                    loadingLayoutMF.visibility = View.GONE
                    fragmentMain.showSnackBar(
                        R.string.snack_text,
                        R.string.snack_action_text
                    )
                }
                is MainState.Loading -> {
                    loadingLayoutMF.visibility = View.VISIBLE
                }
                is MainState.Success -> {
                    loadingLayoutMF.visibility = View.GONE
                    recycleList.visibility = View.VISIBLE
                    adapter.setWeatherList(data.weatherData)
                }
            }
        }
    }

    private fun View.showSnackBar(
        textToDisplay: Int,
        textForAction: Int,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, textToDisplay, length).setAction(textForAction) {
            viewModel.getWeatherRussia()
        }.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(weather: Weather) {
       goToDetailsFragment(weather)
    }

    private fun goToDetailsFragment(weather: Weather){
        requireActivity().supportFragmentManager.beginTransaction().add(
            R.id.fragment_container,
            DetailsFragment.newInstance(Bundle().apply {
                putParcelable(KEY_BUNDLE_WEATHER, weather)
            })
        )
            .addToBackStack("").commit()
    }

    override fun permissionCheck() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getLocation()
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


    override fun showAlertDialog(
        titleText: String,
        messageText: String
    ) {

        AlertDialog.Builder(
            requireContext()
        )
            .setTitle(titleText)
            .setMessage(messageText)
            .setPositiveButton(getString(R.string.yes_alert_button)) { _, _ ->
                runPermissionRequest()
            }
            .setNegativeButton(getString(R.string.no_alert_button)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

   private val locationPermissionRequest = registerForActivityResult(
       ActivityResultContracts.RequestMultiplePermissions()
   ) { permissions ->
       when {
           permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
               getLocation()
           }
           permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
              getLocation()
           } else -> {
           showAlertDialog(
               getString(R.string.GEO_accsess), getString(R.string.GEO_explanation)
           )
       }
       }
   }

    private fun getLocation() {

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED  ) {

                val locationManager =
                    requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val criteria = Criteria()
                criteria.accuracy = Criteria.ACCURACY_FINE
                val provider = locationManager.getBestProvider(criteria, true)
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                    locationManager.isProviderEnabled(LocationManager.FUSED_PROVIDER
                        )) {

                   provider?.let {
                        locationManager.requestLocationUpdates(
                            provider,
                            10000,
                            100f,
                            onLocationListener
                        )
                    }

                } else {
                    val location =
                        provider?.let { locationManager.getLastKnownLocation(it) }
                    if (location == null) {
                       showErrorSnackBar(getString(R.string.GEO_accsess_disabled))
                    } else {
                        showErrorSnackBar(getString(R.string.last_location_shown))
                        getOldLocation(location)
                    }

                }
            } else {
                runPermissionRequest()
            }
    }

    private fun runPermissionRequest(){
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private val onLocationListener = object :LocationListener {
        override fun onLocationChanged(p0: Location) {
            getOldLocation(p0)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
            showErrorSnackBar(getString(R.string.GEO_disabled))
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
            getLocation()
        }
    }

    override fun getOldLocation(it: Location) {
        val geoCoder = Geocoder(requireContext())
        Thread {
            try {
                val addresses = geoCoder.getFromLocation(it.latitude, it.longitude, 1)
requireActivity().runOnUiThread{
    showAddressDialog(addresses[0].getAddressLine(0),it)
}

            } catch (e: IOException) {
                e.printStackTrace()
                showErrorSnackBar(getString(R.string.smth_went_wrong))
            }
        }.start()
    }

    private fun showAddressDialog(address: String, location: Location) {
        AlertDialog.Builder(
            requireContext()
        )
            .setTitle(getString(R.string.adress_question))
            .setMessage(address)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                goToDetailsFragment(Weather(City(address,location.latitude,location.longitude)))
            }
            .setNegativeButton(getString(R.string.no_alert_button)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    private fun showErrorSnackBar(
        textToDisplay: String,
    ) {
        Snackbar.make(binding.fragmentMain, textToDisplay, Snackbar.LENGTH_LONG).show()
        }

}

