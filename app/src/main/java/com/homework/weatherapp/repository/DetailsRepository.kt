package com.homework.weatherapp.repository

import com.homework.weatherapp.model.City
import com.homework.weatherapp.view_model.DetailsViewModel


interface DetailsRepository {
    fun getWeatherDetails(city: City, myCallback: DetailsViewModel.Callback)

}