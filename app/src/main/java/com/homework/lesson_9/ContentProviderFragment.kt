package com.homework.lesson_9

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.homework.weatherapp.R
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
        checkPermissionContacts()

    }

    private fun checkPermissionContacts() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> getContacts()

            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)
            -> showExplanation(
                getString(R.string.contacts_access),
                getString(R.string.contacts_access_message),
                Manifest.permission.READ_CONTACTS, REQUEST_READ_CONTACTS
            )

            else -> askPermission(Manifest.permission.READ_CONTACTS, REQUEST_READ_CONTACTS)
        }


    }

    private fun checkPermissionCall(phone: String) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED -> dialNumber(phone)

            shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> showExplanation(
                getString(R.string.call_access),
                getString(R.string.call_message_text),
                Manifest.permission.CALL_PHONE, REQUEST_CALL_CONTACTS
            )
            else -> askPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_CONTACTS)
        }
    }


    private fun askPermission(permissionType: String, codeRequest: Int) {
        requestPermissions(arrayOf(permissionType), codeRequest)
    }


    private fun showExplanation(
        titleText: String,
        messageText: String,
        permissionType: String,
        codeRequest: Int
    ) {
        AlertDialog.Builder(
            requireContext(),
            R.style.Theme_MaterialComponents_DayNight_Dialog_Alert
        )
            .setTitle(titleText)
            .setMessage(messageText)
            .setPositiveButton(getString(R.string.yes_alert_button)) { _, _ ->
                askPermission(permissionType, codeRequest)
            }
            .setNegativeButton(getString(R.string.no_alert_button)) { dialogInterface, _ ->
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
        when (requestCode) {

            REQUEST_READ_CONTACTS -> {
                for (i in permissions.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        getContacts()
                    } else showExplanation(
                        getString(R.string.contacts_access),
                        getString(R.string.contacts_access_message),
                        Manifest.permission.READ_CONTACTS, REQUEST_READ_CONTACTS
                    )
                }
            }
            REQUEST_CALL_CONTACTS -> {
                for (i in permissions.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        /*"Тут нужно бы вызвать dialNumber(но параметр <phone> тут наверное не передать */

                    } else showExplanation(
                        getString(R.string.call_access),
                        getString(R.string.call_message_text),
                        Manifest.permission.CALL_PHONE, REQUEST_CALL_CONTACTS
                    )
                }
            }
        }
    }

    @SuppressLint("Range")
    private fun getContacts() {
        val contentResolver = requireContext().contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )

        cursor?.let {
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phone =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                addTextView(name, phone)
            }
        }
        cursor?.close()
    }

    @SuppressLint("SetTextI18n")
    private fun addTextView(name: String, phone: String) {
        binding.containerForContacts.addView(TextView(requireContext()).apply {
            text = "Имя: $name\n\"Телефон: $phone\n"
            textSize = 25f
            isClickable = true
            setOnClickListener {

                checkPermissionCall(phone)
            }
        })
    }

    private fun dialNumber(phone: String) {
        activity?.let {
            val dial = "tel:$phone"
            it.startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
        }
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
