package com.homework.weatherapp.repository

import com.homework.weatherapp.model.Weather

interface DetailsRepositoryRoomAdd {
    fun addWeather(weather: Weather)
}