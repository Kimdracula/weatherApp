package com.homework.weatherapp.view_model

import com.homework.weatherapp.model.Weather

sealed class MainState {
    object Loading : MainState()

    data class SuccessLocal(val weatherData: List<Weather>) : MainState()
    data class Error(val error: Throwable) : MainState()

}