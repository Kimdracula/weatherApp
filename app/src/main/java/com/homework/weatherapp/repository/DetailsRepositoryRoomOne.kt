package com.homework.weatherapp.repository

import com.homework.weatherapp.model.City
import com.homework.weatherapp.view_model.DetailsViewModel

interface DetailsRepositoryRoomOne {
        fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback)
}