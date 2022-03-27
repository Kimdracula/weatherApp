package com.homework.weatherapp.repository

import com.homework.weatherapp.model.Weather

class RepositoryImpl : Repository {
    override fun getWeatherByLocal(): Weather {

        Thread.sleep(100)
        return Weather()
    }

    override fun getWeatherByServer(): Weather {
        Thread.sleep(2000)
        return Weather()
    }
}