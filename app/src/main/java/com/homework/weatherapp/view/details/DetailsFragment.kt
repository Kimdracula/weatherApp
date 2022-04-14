package com.homework.weatherapp.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.homework.weatherapp.databinding.FragmentDetailsBinding
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.utils.KEY_BUNDLE_WEATHER
import java.net.URL
import javax.net.ssl.HttpsURLConnection


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
        renderData(requireArguments().getParcelable(KEY_BUNDLE_WEATHER)!!)
    }

    private fun renderData(weather: Weather) {
        with(binding) {
            weather.also {
                infoLayout.visibility = View.VISIBLE
                loadingLayout.visibility = View.GONE
                cityName.text = it.city.name
                coordinates.text =
                    "Широта: ${it.city.lat} Долгота: ${it.city.lon}"
                temperature.text = it.temperature.toString()
                feelsLike.text = it.fellsLike.toString()
            }
        }
    }
}


