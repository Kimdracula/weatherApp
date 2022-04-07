package com.homework.weatherapp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
        val observer = Observer<ResponseState> { it.let { renderData(it) } }
        initViews(observer)
        initDecorator()
        viewModel.getWeatherRussia()
    }

    private fun initViews(observer: Observer<ResponseState>) {
        with(binding) {
            with(viewModel) {
                recycleList.adapter = adapter
                getData().observe(viewLifecycleOwner, observer)
                floatingActionButton.setOnClickListener {
                    isRussian = !isRussian
                    if (isRussian) {
                        getWeatherRussia()
                        floatingActionButton.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.russia_ic
                            )
                        )
                    } else {
                        floatingActionButton.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.earth_ic
                            )
                        )
                        getWeatherWorld()
                    }
                }
            }
        }
    }

    private fun initDecorator() {
        val itemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator, null))
        binding.recycleList.addItemDecoration(itemDecoration)
    }

    private fun renderData(data: ResponseState) {
        with(binding) {
            when (data) {
                is ResponseState.Error -> {
                    loadingLayoutMF.visibility = View.GONE
                    showSnackBar()
                }
                is ResponseState.Loading -> {
                    loadingLayoutMF.visibility = View.VISIBLE
                }
                is ResponseState.Success -> {
                    loadingLayoutMF.visibility = View.GONE
                    recycleList.visibility = View.VISIBLE
                    adapter.setWeatherList(data.weatherData)
                }
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
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            DetailsFragment.newInstance(Bundle().apply {
                putParcelable(KEY_BUNDLE_WEATHER, weather)
            })
        )
            .addToBackStack("").commit()
    }
}

