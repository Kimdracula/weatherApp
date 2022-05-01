package com.homework.weatherapp.repository

import com.homework.weatherapp.view_model.HistoryViewModel

interface DetailsRepositoryRoomAll {
    fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll)
}