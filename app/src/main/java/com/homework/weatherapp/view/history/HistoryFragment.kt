package com.homework.weatherapp.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.homework.weatherapp.R
import com.homework.weatherapp.databinding.FragmentHistoryBinding
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.utils.KEY_BUNDLE_WEATHER
import com.homework.weatherapp.view.details.DetailsFragment
import com.homework.weatherapp.view_model.MainViewModel
import com.homework.weatherapp.view_model.MainState

class HistoryFragment: Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val adapter = HistoryAdapter()


    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val observer = Observer<MainState> { it.let { renderData(it) } }
        initViews(observer)

        initDecorator()

    }


    private fun initDecorator() {
        val itemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator, null))
        binding.recycleListHistory.addItemDecoration(itemDecoration)
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
                is MainState.SuccessLocal -> {
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().add(
            R.id.fragment_container,
            DetailsFragment.newInstance(Bundle().apply {
                putParcelable(KEY_BUNDLE_WEATHER, weather)
            })
        )
            .addToBackStack("").commit()
    }
}