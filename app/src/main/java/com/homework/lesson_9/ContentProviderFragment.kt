package com.homework.lesson_9

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.homework.weatherapp.databinding.FragmentContentProviderBinding


class ContentProviderFragment : Fragment() {
    private var _binding: FragmentContentProviderBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()

    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> getContacts()

            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_CONTACTS) -> showExplanation()

            else -> askPermission()
        }


    }

    private fun askPermission() {
        TODO("Not yet implemented")
    }


    private fun showExplanation() {

    }

    private fun getContacts() {
        TODO("Not yet implemented")
    }


    companion object {
        @JvmStatic
        fun newInstance() = ContentProviderFragment()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
