package com.homework.weatherapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.weatherapp.repository.RepositoryImpl

class MainViewModel(
    private val liveData: MutableLiveData<ResponseState> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    fun getData(): LiveData<ResponseState> {
        return liveData
    }

    fun getWeather() {
        Thread {
            liveData.postValue(ResponseState.Loading)
            when ((0..5).random()) {
                1 -> {
                    repository.getWorldWeatherByLocal()
                    liveData.postValue(ResponseState.Success(repository.getWorldWeatherByLocal()))
                }
                2 -> {
                    repository.getRussianWeatherByLocal()
                    liveData.postValue(ResponseState.Success(repository.getRussianWeatherByLocal()))
                }
                else -> liveData.postValue(ResponseState.Error(IllegalAccessError()))
            }
        }.start()


    }


}
