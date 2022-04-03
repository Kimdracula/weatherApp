package com.homework.weatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.homework.weatherapp.databinding.FragmentMainBinding
import com.homework.weatherapp.view_model.MainViewModel
import com.homework.weatherapp.view_model.ResponseState


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mfAdapter: MfAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recycleList.layoutManager
        binding.recycleList.adapter = mfAdapter

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val observer = object: Observer<ResponseState>{
            override fun onChanged(data: ResponseState?) {
                if (data != null) {
                    renderData(data)
                }
            }

        }
    }

  private fun renderData(data:ResponseState){
      when(data) {

          is ResponseState.Error -> {
              binding.loadingLayoutMF.visibility = View.GONE
              showSnackBar()
          }
          is ResponseState.Loading -> {
              binding.loadingLayoutMF.visibility = View.VISIBLE
          }
          is ResponseState.Success -> {
              binding.loadingLayoutMF.visibility = View.GONE

          }
      }
  }

    private fun showSnackBar() {
        Snackbar.make(
            binding.fragmentMain,
            "Не получилось загрузить данные...",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("Eщё раз?") {
               TODO("ЧТО ТО НАДО СДЕЛАТЬ")
            }
            .show()
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

