package com.homework.weatherapp.view.details

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.homework.weatherapp.model.WeatherDTO
import com.homework.weatherapp.repository.WeatherLoaderResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherLoader(private val wlResponse: WeatherLoaderResponse) {


 fun loadWeather(lat: Double, lon: Double){
Thread{
    var urlConnection: HttpURLConnection? = null
    val url = URL("https://api.weather.yandex.ru/v2/forecast?lat=$lat&lon=$lon")
    urlConnection = url.openConnection() as HttpURLConnection
    urlConnection.apply {
        requestMethod = "GET"
        readTimeout = 10000
        connectTimeout = 10000
        addRequestProperty("X-Yandex-API-Key", "c01aa6be-24ed-4bfe-8cd0-d4290e5837a0")
    }
    val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))

    val weatherDTO:WeatherDTO = Gson().fromJson(reader,WeatherDTO::class.java)
Handler(Looper.getMainLooper()).post{
    wlResponse.displayWeather(weatherDTO)
}

}.start()

 }
}