package com.homework.weatherapp.repository

import com.homework.weatherapp.model.WeatherDTO
import com.homework.weatherapp.view_model.ResponseState

interface WeatherLoaderResponse {

    fun onResponse(weatherDTO: WeatherDTO)
    fun onError(error: ResponseState, responseCode: Int)
}