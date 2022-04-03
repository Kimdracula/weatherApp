package com.homework.weatherapp.repository

import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.model.getRussianCities
import com.homework.weatherapp.model.getWorldCities

class RepositoryImpl : Repository {
    override fun getWeatherByLocal(): Weather {
        return Weather()
    }

    override fun getWorldWeatherByLocal(): List<Weather> {
       return getWorldCities()
    }

    override fun getRussianWeatherByLocal(): List<Weather> {
        return getRussianCities()
    }

}
