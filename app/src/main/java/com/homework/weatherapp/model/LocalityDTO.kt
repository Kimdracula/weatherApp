package com.homework.weatherapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalityDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
):Parcelable