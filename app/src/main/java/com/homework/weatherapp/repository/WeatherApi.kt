package com.homework.weatherapp.repository

import android.os.Build
import com.homework.weatherapp.BuildConfig
import com.homework.weatherapp.model.WeatherDTO
import com.homework.weatherapp.utils.END_POINT
import com.homework.weatherapp.utils.QUERY_LAT
import com.homework.weatherapp.utils.QUERY_LON
import com.homework.weatherapp.utils.WEATHER_API_LOGIN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {
    @GET(END_POINT)
    fun getWeather(
        @Header (WEATHER_API_LOGIN) apikey: String,
        @Query (QUERY_LAT) lat: Double,
        @Query (QUERY_LON) lon: Double
    ):Call<WeatherDTO>

}