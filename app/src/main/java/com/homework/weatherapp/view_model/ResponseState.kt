package com.homework.weatherapp.view_model

import com.homework.weatherapp.model.Weather

sealed class ResponseState {
    object Loading : ResponseState()

    data class SuccessLocal(val weatherData: List<Weather>) : ResponseState()
    data class Error(val error: Throwable) : ResponseState()

}