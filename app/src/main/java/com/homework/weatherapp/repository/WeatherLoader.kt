package com.homework.weatherapp.repository

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.homework.weatherapp.BuildConfig
import com.homework.weatherapp.model.WeatherDTO
import com.homework.weatherapp.view_model.ResponseState
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherLoader(private val wlResponse: WeatherLoaderResponse) {


    private var urlConnection: HttpURLConnection? = null
    private var responseCode: Int? = null

    fun loadWeather(lat: Double, lon: Double) {
        Thread {
            try {
              // val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon&[lang=ru_RU]")
                val url = URL("http://212.86.114.27/v2/informers?lat=$lat&lon=$lon")

                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection?.apply {
                    requestMethod = "GET"
                    readTimeout = 10000
                    connectTimeout = 10000
                    addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
                }
                responseCode = urlConnection!!.responseCode

            } catch (IOEx: IOException) {
                responseCode?.let { wlResponse.onError(ResponseState.Error(IOEx), it) }
            } catch (ex: Exception) {
                responseCode?.let { wlResponse.onError(ResponseState.Error(ex), it) }
            }
            try {
                val reader = BufferedReader(InputStreamReader(urlConnection!!.inputStream))
                val weatherDTO: WeatherDTO = Gson().fromJson(reader, WeatherDTO::class.java)
                Handler(Looper.getMainLooper()).post {
                    //wlResponse.displayWeather(weatherDTO)
                }
                urlConnection!!.disconnect()
            } catch (numberFormatEx: NumberFormatException) {
                responseCode?.let { wlResponse.onError(ResponseState.Error(numberFormatEx), it) }
            } catch (IOex: IOException) {
                responseCode?.let { wlResponse.onError(ResponseState.Error(IOex), it) }
            } catch (illegalStateEx: IllegalStateException) {
                responseCode?.let { wlResponse.onError(ResponseState.Error(illegalStateEx), it) }
            } catch (ex: Exception) {
                responseCode?.let { wlResponse.onError(ResponseState.Error(ex), it) }
            }

        }.start()

    }
}