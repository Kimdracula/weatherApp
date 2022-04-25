package com.homework.weatherapp.view_model

import com.homework.weatherapp.model.Weather

sealed class DetailsState {
    object Loading:DetailsState()
    data class Success(val weather: Weather):DetailsState()
    data class Error(val error:Throwable):DetailsState()
}