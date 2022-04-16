package com.homework.weatherapp.view.details

import android.annotation.SuppressLint
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

    @SuppressLint("SetTextI18n")
    private fun renderData(weather: Weather) {
        WeatherLoader(this).loadWeather(weather.city.lat, weather.city.lon)
        binding.cityName.text = weather.city.name
        binding.coordinates.text =
            "Широта: ${weather.city.lat},\nДолгота: ${weather.city.lon}"
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


