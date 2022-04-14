package com.homework.weatherapp.model

data class Forecast(
    val biomet: Biomet,
    val date: String,
    val date_ts: Int,
    val hours: List<Hour>,
    val moon_code: Int,
    val moon_text: String,
    val parts: Parts,
    val rise_begin: String,
    val set_end: String,
    val sunrise: String,
    val sunset: String,
    val week: Int
)