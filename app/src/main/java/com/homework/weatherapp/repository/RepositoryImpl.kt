package com.homework.weatherapp.repository

import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.model.getRussianCities
import com.homework.weatherapp.model.getWorldCities

class RepositoryImpl : Repository {
    override fun getWeatherByLocal(): Weather = Weather()

    override fun getWorldWeatherByLocal(): List<Weather> = getWorldCities()

    override fun getRussianWeatherByLocal(): List<Weather> = getRussianCities()
}
