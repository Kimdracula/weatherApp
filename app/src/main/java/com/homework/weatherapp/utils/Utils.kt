package com.homework.weatherapp.utils

import com.homework.weatherapp.model.FactDTO
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.model.WeatherDTO
import com.homework.weatherapp.model.getDefaultCity

const val KEY_BUNDLE_WEATHER = "key"
const val SCHEME = "https:"
const val AUTHORITY = "api.weather.yandex.ru"
const val END_POINT = "v2/informers?"
const val QUERY_LAT = "lat"
const val QUERY_LON = "lon"
const val WEATHER_API_LOGIN = "X-Yandex-API-Key"



fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    val fact: FactDTO = weatherDTO.factDTO
    return (Weather(getDefaultCity(), fact.temp, fact.feelsLike, fact.condition, fact.humidity,fact.icon))
}

