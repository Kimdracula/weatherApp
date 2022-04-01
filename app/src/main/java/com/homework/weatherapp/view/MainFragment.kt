package com.homework.weatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homework.weatherapp.R
import com.homework.weatherapp.databinding.FragmentDetailsBinding
import com.homework.weatherapp.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mfAdapter: MfAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recycleList.layoutManager
    //   mfAdapter = MfAdapter()
        binding.recycleList.adapter = mfAdapter
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

