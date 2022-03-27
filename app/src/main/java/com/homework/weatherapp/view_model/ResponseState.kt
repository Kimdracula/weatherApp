package com.homework.weatherapp.view_model

import com.homework.weatherapp.model.Weather

sealed class ResponseState {
    object Loading : ResponseState()

    data class Success(val weatherData: Weather) : ResponseState()

    data class Error(val error: Throwable) : ResponseState()


}