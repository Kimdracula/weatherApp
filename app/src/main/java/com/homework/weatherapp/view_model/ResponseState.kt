package com.homework.weatherapp.view_model

import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.model.WeatherDTO

sealed class ResponseState {
    object Loading : ResponseState()

    data class SuccessLocal(val weatherData: List<Weather>) : ResponseState()

    data class SuccessServer(val weatherDTO: WeatherDTO) : ResponseState()

    data class Error(val error: Throwable) : ResponseState()



}