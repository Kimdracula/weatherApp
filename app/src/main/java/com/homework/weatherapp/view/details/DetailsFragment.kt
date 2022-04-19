package com.homework.weatherapp.view.details

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import com.homework.weatherapp.databinding.FragmentDetailsBinding
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.model.WeatherDTO
import com.homework.weatherapp.repository.LoaderExceptions
import com.homework.weatherapp.repository.WeatherLoaderResponse
import com.homework.weatherapp.utils.*
import com.homework.weatherapp.view_model.ResponseState


class DetailsFragment : Fragment(), WeatherLoaderResponse {


    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(serviceResponse,
            IntentFilter(KEY_BROADCAST_INTENT)
        )
        renderData(requireArguments().getParcelable(KEY_BUNDLE_WEATHER)!!)

    }

    @SuppressLint("SetTextI18n")
    private fun renderData(weather: Weather) {

        requireActivity().startService(Intent(requireContext(),DetailsService::class.java).apply {
           putExtra(KEY_INTENT_LAT,weather.city.lat)
            putExtra(KEY_INTENT_LON,weather.city.lon)
        })

        binding.cityName.text = weather.city.name
        binding.coordinates.text =
            "Широта: ${weather.city.lat},\nДолгота: ${weather.city.lon}"
    }

    private val serviceResponse = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            p1?.let {
              val loadedWeather = it.getParcelableExtra<WeatherDTO>(KEY_BROADCAST_MESSAGE)
                if (loadedWeather != null) {
                    displayWeather(loadedWeather)
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun displayWeather(weatherDTO: WeatherDTO) {
        with(binding) {
            infoLayout.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE
            temperature.text = weatherDTO.factDTO.temp.toString()
            feelsLike.text = weatherDTO.factDTO.feelsLike.toString()
            condition.text = weatherDTO.factDTO.condition
            humidity.text = "${weatherDTO.factDTO.humidity} %"
        }
    }

    override fun onError(error: ResponseState, responseCode: Int) {
       Snackbar.make(binding.root, LoaderExceptions().check(responseCode), Snackbar.LENGTH_LONG).show()
        Log.d("!!!", "$error Код ошибки: $responseCode ")
    }
}


