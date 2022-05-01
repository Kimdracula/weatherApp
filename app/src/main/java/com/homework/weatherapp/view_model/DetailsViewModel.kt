package com.homework.weatherapp.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.weatherapp.model.City
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.repository.*
import com.homework.weatherapp.utils.SERVER_ERROR

class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
private val repositoryAdd: DetailsRepositoryRoomAdd = DetailsRepositoryRoomImpl()
) : ViewModel() {
    private var repository: DetailsRepository = DetailsRepositoryRetrofitImpl()

    fun getLiveDataDetails()= liveData

fun getWeather(city: City){
    liveData.postValue(DetailsState.Loading)
    repository = if (isInternet()) {
DetailsRepositoryRetrofitImpl()
    }else{
        DetailsRepositoryRoomImpl()
    }
    repository.getWeatherDetails(city, object : Callback {
        override fun onResponse(weather: Weather) {
            liveData.postValue(DetailsState.Success(weather))
            if (isInternet()){
                repositoryAdd.addWeather(weather)
        }
    }

        override fun onFail(throwable: Throwable) {
                liveData.postValue(DetailsState.Error(Throwable(SERVER_ERROR)))
        }
    })
}

    private fun isInternet(): Boolean {
        //!!! заглушка
        return true
    }

    interface Callback {
        fun onResponse(weather: Weather)
        fun onFail(throwable: Throwable)
    }
}