package com.homework.lesson_9

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> getContacts()

            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> showExplanation()

            else -> askPermission()
        }


    }

    private fun askPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
    }


    private fun showExplanation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Доступ к контактам")
            .setMessage("Требуется доступ к контактам, в случае отказа в доступе приложение не будет работать")
            .setPositiveButton("Да, разрешаю") { _, _ ->
                askPermission()
            }
            .setNegativeButton("Нет") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    getContacts()
                } else showExplanation()
            }
        } else return

    }

    private fun getContacts() {
        val contentResolver = requireContext().contentResolver
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )
        cursor?.let {
            for (i in 0 until it.count) {
                if (it.moveToPosition(i)) {
                    val columnNameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    val name: String = it.getString(columnNameIndex)
                    addTextView(name)
                }
            }
        }
        cursor?.close()
    }

    private fun addTextView(name: String) {
        binding.containerForContacts.addView(TextView(requireContext()).apply {
            text = name
            textSize = 30f
        })
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