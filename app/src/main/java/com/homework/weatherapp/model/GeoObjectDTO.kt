package com.homework.weatherapp.model


import com.google.gson.annotations.SerializedName

data class GeoObjectDTO(
    @SerializedName("country")
    val countryDTO: CountryDTO,
    @SerializedName("locality")
    val localityDTO: LocalityDTO
)