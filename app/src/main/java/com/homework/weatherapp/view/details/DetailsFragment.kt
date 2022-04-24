package com.homework.weatherapp.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.android.material.snackbar.Snackbar
import com.homework.weatherapp.databinding.FragmentDetailsBinding
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.repository.LoaderExceptions
import com.homework.weatherapp.utils.KEY_BUNDLE_WEATHER
import com.homework.weatherapp.view_model.DetailsState
import com.homework.weatherapp.view_model.DetailsViewModel


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }


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
        viewModel.getLiveDataDetails().observe(viewLifecycleOwner
        ) { d -> renderData(d) }
        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            viewModel.getWeather(it.city)
        }
    }

  @SuppressLint("SetTextI18n")
  private  fun renderData(detailsState: DetailsState) {
        when (detailsState) {
            is DetailsState.Error -> {

            }
            DetailsState.Loading -> {

            }
            is DetailsState.Success -> {
                val weather = detailsState.weather
                with(binding) {
                    infoLayout.visibility = View.VISIBLE
                    loadingLayout.visibility = View.GONE
                    cityName.text = weather.city.name
                    coordinates.text = "Широта: ${weather.city.lat}\nДолгота: ${weather.city.lon}"
                    temperature.text = weather.temperature.toString()
                    feelsLike.text = weather.fellsLike.toString()
                    condition.text = weather.condition
                    humidity.text = weather.humidity.toString()
                    loadSvg("https://yastatic.net/weather/i/icons/blueye/color/svg/${weather.icon}.svg")
                }
            }
        }

    }

   private fun loadSvg(url: String) {
        val imageLoader = ImageLoader.Builder(requireContext())
            .components {
                add(SvgDecoder.Factory())
            }
            .crossfade(true)
            .build()
        val request = ImageRequest.Builder(requireContext())
            .data(url)
            .crossfade(true)
            .target(binding.weatherIcon)
            .build()
        imageLoader.enqueue(request)
    }

        fun showErrorSnack(responseCode: Int) {
            Snackbar.make(
                binding.root,
                LoaderExceptions().check(responseCode),
                Snackbar.LENGTH_LONG
            ).show()
        }


}



