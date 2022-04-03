package com.homework.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.homework.weatherapp.databinding.FragmentDetailsBinding
import com.homework.weatherapp.view_model.MainViewModel
import com.homework.weatherapp.view_model.ResponseState


class DetailsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
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
        fun newInstance() =
            DetailsFragment().apply {
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val observer = Observer<ResponseState> {
            renderData(it)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getWeather()
    }

    private fun renderData(data: ResponseState) {
        when (data) {
            is ResponseState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                showSnackBar()
            }
            is ResponseState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is ResponseState.Success -> {
                binding.infoLayout.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.cityName.text = data.weatherData.city.name
                binding.coordinates.text =
                    "Широта: ${data.weatherData.city.lat} Долгота: ${data.weatherData.city.lon}"
                binding.temperature.text = data.weatherData.temperature.toString()
                binding.feelsLike.text = data.weatherData.fellsLike.toString()

            }
        }

    }

    private fun showSnackBar() {
        Snackbar.make(
            binding.mainView,
            "Не получилось загрузить данные...",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("Eщё раз?") {
                viewModel.getWeather()
            }
            .show()
    }
}
