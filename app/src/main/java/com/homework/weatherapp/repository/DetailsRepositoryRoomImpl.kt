package com.homework.weatherapp.repository

import com.homework.weatherapp.App
import com.homework.weatherapp.model.City
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.utils.EMPTY_LIST_ERROR
import com.homework.weatherapp.utils.convertHistoryEntityToWeather
import com.homework.weatherapp.utils.convertWeatherToEntity
import com.homework.weatherapp.view_model.DetailsViewModel
import com.homework.weatherapp.view_model.HistoryViewModel

class DetailsRepositoryRoomImpl: DetailsRepositoryRoomOne,DetailsRepositoryRoomAll,DetailsRepositoryRoomAdd,
    DetailsRepository {
    override fun addWeather(weather: Weather) {
        Thread{
        App.getHistoryDAO().insert(convertWeatherToEntity(weather))
        }.start()
    }

    override fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll) {
        Thread{
        callback.onResponse(convertHistoryEntityToWeather(App.getHistoryDAO().getAll()))
        }.start()
    }

    override fun getWeatherDetails(city: City, myCallback: DetailsViewModel.Callback) {
        Thread {
            val list =
                convertHistoryEntityToWeather(App.getHistoryDAO().getHistoryForCity(city.name))
            if (list.isEmpty()) {
                myCallback.onFail(Throwable(EMPTY_LIST_ERROR))
            } else {
                myCallback.onResponse(list.last()) // FIXME hack
            }
        }.start()
    }
}