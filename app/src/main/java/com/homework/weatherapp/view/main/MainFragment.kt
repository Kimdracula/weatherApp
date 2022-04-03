package com.homework.weatherapp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.homework.weatherapp.R
import com.homework.weatherapp.databinding.FragmentMainBinding
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.utils.KEY_BUNDLE_WEATHER
import com.homework.weatherapp.view.details.DetailsFragment
import com.homework.weatherapp.view_model.MainViewModel
import com.homework.weatherapp.view_model.ResponseState


class MainFragment : Fragment(), OnItemListClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    var isRussian = true
    private val adapter = MfAdapter(this)
    private lateinit var viewModel: MainViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val observer = object : Observer<ResponseState> {
            override fun onChanged(data: ResponseState?) {
                if (data != null) {
                    renderData(data)
                }
            }

        }
        binding.recycleList.adapter = adapter
        viewModel.getData().observe(viewLifecycleOwner, observer)

        binding.floatingActionButton.setOnClickListener {
            isRussian = !isRussian
            if (isRussian) {
                viewModel.getWeatherRussia()
                binding.floatingActionButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.russia_ic
                    )
                )
            } else {
                binding.floatingActionButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.earth_ic
                    )
                )
                viewModel.getWeatherWorld()
            }
        }


        viewModel.getWeatherRussia()
    }

    private fun renderData(data: ResponseState) {
        when (data) {

            is ResponseState.Error -> {
                binding.loadingLayoutMF.visibility = View.GONE
                showSnackBar()
            }
            is ResponseState.Loading -> {
                binding.loadingLayoutMF.visibility = View.VISIBLE
            }
            is ResponseState.Success -> {
                binding.loadingLayoutMF.visibility = View.GONE
                binding.recycleList.visibility = View.VISIBLE
                adapter.setWeatherList(data.weatherData)


            }
        }
    }

    private fun showSnackBar() {
        Snackbar.make(
            binding.fragmentMain,
            "Не получилось загрузить данные...",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Eщё раз?") {
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
        val bundle = Bundle()
        bundle.putParcelable(KEY_BUNDLE_WEATHER, weather)
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            DetailsFragment.newInstance(bundle))
        .addToBackStack("").commit()
    }
}

