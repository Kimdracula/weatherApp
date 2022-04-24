package com.homework.weatherapp.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.weatherapp.model.City
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.repository.DetailsRepositoryRetrofitImpl

class DetailsViewModel(private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
private val repository: DetailsRepositoryRetrofitImpl = DetailsRepositoryRetrofitImpl()
) : ViewModel() {


    fun getLiveDataDetails()= liveData

fun getWeather(city: City){
    liveData.postValue(DetailsState.Loading)
    repository.getWeatherDetails(city, object : Callback {
        override fun onResponse(weather: Weather) {
            liveData.postValue(DetailsState.Success(weather))
        }

        override fun onFail() {
            TODO("Not yet implemented")
        }
    })
}

    interface Callback {
        fun onResponse(weather: Weather)

        fun onFail()
    }
}