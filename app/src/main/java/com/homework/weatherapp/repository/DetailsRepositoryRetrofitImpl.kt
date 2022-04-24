package com.homework.weatherapp.repository

import com.google.gson.GsonBuilder
import com.homework.weatherapp.BuildConfig
import com.homework.weatherapp.model.City
import com.homework.weatherapp.model.WeatherDTO
import com.homework.weatherapp.utils.AUTHORITY
import com.homework.weatherapp.utils.SCHEME
import com.homework.weatherapp.utils.convertDtoToModel
import com.homework.weatherapp.view_model.DetailsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsRepositoryRetrofitImpl : DetailsRepository {
    override fun getWeatherDetails(city: City, myCallback: DetailsViewModel.Callback) {
        val weatherApi = Retrofit.Builder().apply {
            baseUrl("$SCHEME//$AUTHORITY")
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        }.build().create(WeatherApi::class.java)

        weatherApi.getWeather(BuildConfig.WEATHER_API_KEY, city.lat, city.lon)
            .enqueue(object : Callback<WeatherDTO> {
                override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val weather = convertDtoToModel(it)
                            weather.city = city
                            myCallback.onResponse(weather)
                        }
                    } else {
                        myCallback.onFail()
                    }
                }
                override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                    myCallback.onFail()
                }
            })
    }

}