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

    fun getWeatherRussia() = getWeather(true)
    fun getWeatherWorld() = getWeather(false)

    private fun getWeather(isRussian:Boolean) {
        Thread {
            liveData.postValue(ResponseState.Loading)
            val answer = if(!isRussian) repository.getWorldWeatherByLocal()
            else repository.getRussianWeatherByLocal()
            liveData.postValue(ResponseState.Success(answer))
        }.start()


    }


}
