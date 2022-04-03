package com.homework.weatherapp.view

import com.homework.weatherapp.model.Weather

interface OnItemListClickListener {
    fun onItemClick(weather: Weather)
}