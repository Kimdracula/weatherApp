package com.homework.weatherapp.view.main

import android.location.Location
import com.homework.weatherapp.model.Weather

interface OnItemListClickListener {
    fun onItemClick(weather: Weather)
    fun permissionCheck()
    fun showAlertDialog(titleText: String,
                        messageText: String)

    fun getOldLocation(it: Location)
}