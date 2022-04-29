package com.homework.weatherapp.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.homework.weatherapp.R
import com.homework.weatherapp.databinding.FragmentHistoryBinding
import com.homework.weatherapp.view_model.HistoryViewModel
import com.homework.weatherapp.view_model.MainState

class HistoryFragment: Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val adapter = HistoryAdapter()


    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
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
        binding.recycleListHistory.also {// TODO HW вынесты в initRecycler()
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
        val observer = {data: MainState -> renderData(data)}
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getAll()

        initDecorator()

    }

    private fun renderData(data: MainState) {
        when (data) {
            is MainState.Error -> {
                binding.recycleListHistory.visibility = View.GONE
                binding.loadingLayoutHistory.visibility = View.GONE
                Snackbar.make(binding.root, "Не получилось ${data.error}", Snackbar.LENGTH_LONG)
                    .show()
            }
            is MainState.Loading -> {
                binding.recycleListHistory.visibility = View.GONE
                binding.loadingLayoutHistory.visibility = View.VISIBLE
            }
            is MainState.Success -> {
                binding.recycleListHistory.visibility = View.VISIBLE
                binding.loadingLayoutHistory.visibility = View.GONE
                adapter.setData(data.weatherData)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }

    private fun initDecorator() {
        val itemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator, null))
        binding.recycleListHistory.addItemDecoration(itemDecoration)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}