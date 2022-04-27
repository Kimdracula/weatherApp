package com.homework.weatherapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.weatherapp.repository.RepositoryImpl

class MainViewModel(
    private val liveData: MutableLiveData<MainState> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    fun getData(): LiveData<MainState> = liveData

    fun getWeatherRussia() = getWeather(true)
    fun getWeatherWorld() = getWeather(false)

    private fun getWeather(isRussian: Boolean) {
        Thread {
            liveData.run {
                postValue(MainState.Loading)
                val answer = if (!isRussian) repository.getWorldWeatherByLocal()
                else repository.getRussianWeatherByLocal()
                postValue(MainState.Success(answer))
            }
        }.start()
    }
}
