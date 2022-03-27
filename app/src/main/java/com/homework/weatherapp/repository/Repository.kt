package com.homework.weatherapp.repository

import com.homework.weatherapp.model.Weather

interface Repository {
    fun getWeatherByLocal(): Weather
    fun getWeatherByServer():Weather
}