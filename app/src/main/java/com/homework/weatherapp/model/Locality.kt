package com.homework.weatherapp.model


import com.google.gson.annotations.SerializedName

data class Locality(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)