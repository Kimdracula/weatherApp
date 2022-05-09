package com.homework.weatherapp.view.main

import com.homework.weatherapp.model.Weather

interface OnItemListClickListener {
    fun onItemClick(weather: Weather)
    fun permissionCheck()
    fun showAlertDialog(titleText: String,
                        messageText: String)
}