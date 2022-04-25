package com.homework.weatherapp.repository

import com.google.gson.GsonBuilder
import com.homework.weatherapp.model.City
import com.homework.weatherapp.utils.AUTHORITY
import com.homework.weatherapp.utils.SCHEME
import com.homework.weatherapp.view_model.DetailsViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface DetailsRepository {
    fun getWeatherDetails(city: City, myCallback: DetailsViewModel.Callback)

}