package com.homework.weatherapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherDTO(
    @SerializedName("fact")
    val factDTO: FactDTO,

    @SerializedName("geo_object")
    val geoObjectDTO: GeoObjectDTO,
    @SerializedName("info")
    val infoDTO: InfoDTO,
    @SerializedName("now")
    val now: Int,
    @SerializedName("now_dt")
    val nowDt: String
):Parcelable