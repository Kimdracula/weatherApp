package com.homework.weatherapp.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.homework.weatherapp.databinding.FragmentDetailsBinding
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.utils.KEY_BUNDLE_WEATHER


class DetailsFragment : Fragment() {


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
        val weather: Weather = requireArguments().getParcelable<Weather>(KEY_BUNDLE_WEATHER)!!
        renderData(weather)
    }

    private fun renderData(weather: Weather) {

        binding.infoLayout.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE
        binding.cityName.text = weather.city.name
        binding.coordinates.text =
            "Широта: ${weather.city.lat} Долгота: ${weather.city.lon}"
        binding.temperature.text = weather.temperature.toString()
        binding.feelsLike.text = weather.fellsLike.toString()

    }
}


