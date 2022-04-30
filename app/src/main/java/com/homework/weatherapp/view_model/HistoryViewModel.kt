package com.homework.weatherapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.repository.DetailsRepositoryRoomImpl
import com.homework.weatherapp.utils.MESSAGE_ERROR

class HistoryViewModel(
    private val liveData: MutableLiveData<MainState> = MutableLiveData(),
    private val repository: DetailsRepositoryRoomImpl = DetailsRepositoryRoomImpl()
) :
    ViewModel() {
    fun getData(): LiveData<MainState> {
        return liveData
    }

    fun getAll() {
        repository.getAllWeatherDetails(object : CallbackForAll {
            override fun onResponse(listWeather: List<Weather>) {
                liveData.postValue(MainState.Success(listWeather))
            }

            override fun onFail() {
                liveData.postValue(MainState.Error(Throwable(MESSAGE_ERROR)))
            }

        })
    }

    interface CallbackForAll {
        fun onResponse(listWeather: List<Weather>)
        fun onFail()
    }
}
