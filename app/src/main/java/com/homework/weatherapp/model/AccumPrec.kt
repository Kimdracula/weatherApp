package com.homework.weatherapp.model


import com.google.gson.annotations.SerializedName

data class AccumPrec(
    @SerializedName("1")
    val x1: Int,
    @SerializedName("3")
    val x3: Double,
    @SerializedName("7")
    val x7: Double
)