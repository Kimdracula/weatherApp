package com.homework.weatherapp.utils

import com.homework.weatherapp.model.FactDTO
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.model.WeatherDTO
import com.homework.weatherapp.model.getDefaultCity

const val KEY_BUNDLE_WEATHER = "key"
const val KEY_INTENT_LAT = "keyLat"
const val KEY_INTENT_LON = "keyLon"
const val KEY_BROADCAST_INTENT = "keyBroadcastIntent"
const val KEY_BROADCAST_MESSAGE = "keyBroadcastMessage"
const val SCHEME = "https:"
const val AUTHORITY = "api.weather.yandex.ru"
const val END_POINT = "v2/informers?"
const val QUERY_LAT = "lat"
const val QUERY_LON = "lon"
const val WEATHER_API_LOGIN = "X-Yandex-API-Key"
const val KEY_BROADCAST_ERROR_MESSAGE = "keyBroadcastErrorMessage"


fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    val fact: FactDTO = weatherDTO.factDTO
    return (Weather(getDefaultCity(), fact.temp, fact.feelsLike,fact.icon))
}

