package com.homework.weatherapp.model

data class Weather(val city:City =getDefaultCity(), val temperature: Int =-20, val fellsLike :Int=-30)

data class City(val name:String,val lat:Double,val lon:Double)

fun getDefaultCity():City{
    return City("Saint-Petersburg",59.9339,30.3061)}

