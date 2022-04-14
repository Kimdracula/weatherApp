package com.homework.weatherapp.model

data class WeatherDTO(
    val fact: Fact,
    val forecasts: List<Forecast>,
    val geo_object: GeoObject,
    val info: Info,
    val now: Int,
    val now_dt: String,
    val yesterday: Yesterday
)