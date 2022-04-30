package com.homework.weatherapp.utils

import com.homework.weatherapp.domain.room.HistoryEntity
import com.homework.weatherapp.model.*

const val KEY_BUNDLE_WEATHER = "key"
const val SCHEME = "https:"
const val AUTHORITY = "api.weather.yandex.ru"
const val AUTHORITY_ICON = "yastatic.net"
const val END_POINT_ICON = "weather/i/icons/blueye/color/svg"
const val FORMAT_ICON = "svg"
const val END_POINT = "v2/informers?"
const val QUERY_LAT = "lat"
const val QUERY_LON = "lon"
const val WEATHER_API_KEY = "X-Yandex-API-Key"
const val SERVER_ERROR = "Сервер не отвечает"
const val NO_DATA = "Ошибка подключения"
const val MESSAGE_ERROR = "Что то пошло не так"
const val SHARED_PREF_KEY = "prefKey"



fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    val fact: FactDTO = weatherDTO.factDTO
    return (Weather(getDefaultCity(), fact.temp, fact.feelsLike, fact.condition, fact.humidity,fact.icon))
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(City(it.city, 0.0, 0.0), it.temperature, it.fellsLike, it.condition,it.humidity, it.icon)
    }
}

fun convertWeatherToEntity(weather: Weather): HistoryEntity {
    return HistoryEntity( weather.city.name,weather.city.lat,weather.city.lon, weather.temperature,weather.fellsLike,
        weather.condition,weather.humidity,System.currentTimeMillis(), weather.icon)
}