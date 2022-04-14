package com.homework.weatherapp.repository

import com.homework.weatherapp.model.WeatherDTO

interface WeatherLoaderResponse {

    fun displayWeather(weatherDTO: WeatherDTO)
}