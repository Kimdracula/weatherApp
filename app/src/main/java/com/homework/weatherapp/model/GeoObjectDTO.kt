package com.homework.weatherapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeoObjectDTO(
    @SerializedName("country")
    val countryDTO: CountryDTO,
    @SerializedName("locality")
    val localityDTO: LocalityDTO
):Parcelable