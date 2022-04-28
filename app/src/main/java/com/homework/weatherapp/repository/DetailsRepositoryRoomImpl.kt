package com.homework.weatherapp.repository

import com.homework.weatherapp.App
import com.homework.weatherapp.model.City
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.utils.convertHistoryEntityToWeather
import com.homework.weatherapp.utils.convertWeatherToEntity
import com.homework.weatherapp.view_model.DetailsViewModel
import com.homework.weatherapp.view_model.HistoryViewModel

class DetailsRepositoryRoomImpl: DetailsRepositoryRoomOne,DetailsRepositoryRoomAll,DetailsRepositoryRoomAdd,
    DetailsRepository {
    override fun addWeather(weather: Weather) {
        App.getHistoryDAO().insert(convertWeatherToEntity(weather))
    }

    override fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll) {
        callback.onResponse(convertHistoryEntityToWeather(App.getHistoryDAO().getAll()))
    }

    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val list =convertHistoryEntityToWeather(App.getHistoryDAO().getHistoryForCity(city.name))
        if(list.isEmpty()){
            callback.onFail(Throwable("Список пуст"))
        }else{
            callback.onResponse(list.last()) // FIXME hack
        }
    }
}