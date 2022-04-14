package com.homework.weatherapp.model

data class Hour(
    val cloudness: Int,
    val condition: String,
    val feels_like: Int,
    val hour: String,
    val hour_ts: Int,
    val humidity: Int,
    val icon: String,
    val is_thunder: Boolean,
    val prec_mm: Int,
    val prec_period: Int,
    val prec_prob: Int,
    val prec_strength: Int,
    val prec_type: Int,
    val pressure_mm: Int,
    val pressure_pa: Int,
    val soil_moisture: Double,
    val soil_temp: Int,
    val temp: Int,
    val uv_index: Int,
    val wind_dir: String,
    val wind_gust: Double,
    val wind_speed: Double
)