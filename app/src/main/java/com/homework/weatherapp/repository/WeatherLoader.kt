package com.homework.weatherapp.repository

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.homework.weatherapp.model.WeatherDTO
import com.homework.weatherapp.view_model.ResponseState
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.properties.Delegates

class WeatherLoader(private val wlResponse: WeatherLoaderResponse) {



    private var urlConnection: HttpURLConnection? = null
private var responseCode:Int? = null

    fun loadWeather(lat: Double, lon: Double){
Thread{
    try {
        val url = URL("https://api.weather.yandex.ru/v2/forecast?lat=$lat&lon=$lon&[lang=ru_RU]")
       // val url = URL("http://212.86.114.27/v2/forecast?lat=$lat&lon=$lon")

        urlConnection = url.openConnection() as HttpURLConnection
        urlConnection?.apply {
            requestMethod = "GET"
            readTimeout = 10000
            connectTimeout = 10000
            addRequestProperty("X-Yandex-API-Key", "c01aa6be-24ed-4bfe-8cd0-d4290e5837a0")
        }
        responseCode = urlConnection!!.responseCode

    }catch (ex: IOException){
        responseCode?.let { wlResponse.onError(ResponseState.Error(ex), it) }
    }
    try {
        val reader = BufferedReader(InputStreamReader(urlConnection!!.inputStream))
        val weatherDTO:WeatherDTO = Gson().fromJson(reader,WeatherDTO::class.java)
        Handler(Looper.getMainLooper()).post{
            wlResponse.displayWeather(weatherDTO)
        }
        urlConnection!!.disconnect()
    }catch (numberFormatEx: NumberFormatException){
        responseCode?.let { wlResponse.onError(ResponseState.Error(numberFormatEx), it) }
    }catch (ex:IOException){
        responseCode?.let { wlResponse.onError(ResponseState.Error(ex), it) }
    }

}.start()

 }
}