package com.homework.weatherapp.view.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.homework.weatherapp.databinding.FragmentDetailsBinding
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.model.WeatherDTO
import com.homework.weatherapp.repository.LoaderExceptions
import com.homework.weatherapp.repository.WeatherLoader
import com.homework.weatherapp.repository.WeatherLoaderResponse
import com.homework.weatherapp.utils.KEY_BUNDLE_WEATHER
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
        renderData(requireArguments().getParcelable(KEY_BUNDLE_WEATHER)!!)
    }

    private fun renderData(weather: Weather) {
        WeatherLoader(this).loadWeather(weather.city.lat, weather.city.lon)
        binding.cityName.text = weather.city.name
        binding.coordinates.text =
            "Широта: ${weather.city.lat} Долгота: ${weather.city.lon}"
    }

    override fun displayWeather(weatherDTO: WeatherDTO) {
        with(binding) {
            infoLayout.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE
            temperature.text = weatherDTO.fact.temp.toString()
            feelsLike.text = weatherDTO.fact.feelsLike.toString()
            condition.text = weatherDTO.fact.condition
            humidity.text = "${weatherDTO.fact.humidity.toString()} %"
        }
    }

    override fun onError(error: ResponseState, responseCode: Int) {

        LoaderExceptions().check(responseCode)
            ?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show() }
        Log.d("@@@", "$error Код ошибки: $responseCode ")
    }
}


