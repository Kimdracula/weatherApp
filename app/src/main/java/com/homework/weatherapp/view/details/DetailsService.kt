package com.homework.weatherapp.view.details

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.homework.weatherapp.BuildConfig
import com.homework.weatherapp.model.WeatherDTO
import com.homework.weatherapp.repository.WeatherLoaderResponse
import com.homework.weatherapp.utils.*
import com.homework.weatherapp.view_model.ResponseState
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailsService : IntentService("DetailsService") {

    private var wlResponse:WeatherLoaderResponse? = null
    private var urlConnection: HttpsURLConnection? = null
    private var responseCode: Int? = null

    override fun onHandleIntent(p0: Intent?) {
        p0?.let { intent ->
            try {
            val lat = intent.getDoubleExtra(KEY_INTENT_LAT, 0.00)
            val lon = intent.getDoubleExtra(KEY_INTENT_LON, 0.00)
            val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon&[lang=ru_RU]")
           // val url = URL("https://212.86.114.27/v2/informers?lat=$lat&lon=$lon")


            urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection?.apply {
                requestMethod = "GET"
                readTimeout = 10000
                connectTimeout = 10000
                addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
            }
            responseCode = urlConnection!!.responseCode

        } catch (IOEx: IOException) {
            responseCode?.let { wlResponse?.onError(ResponseState.Error(IOEx), it) }
        } catch (ex: Exception) {
            responseCode?.let { wlResponse?.onError(ResponseState.Error(ex), it) }
        }
        try {
            val reader = BufferedReader(InputStreamReader(urlConnection!!.inputStream))
            val weatherDTO: WeatherDTO = Gson().fromJson(reader, WeatherDTO::class.java)

            val message = Intent(KEY_BROADCAST_INTENT)
            message.putExtra(KEY_BROADCAST_MESSAGE,weatherDTO)
            LocalBroadcastManager.getInstance(this).sendBroadcast(message)
urlConnection!!.disconnect()
        } catch (numberFormatEx: NumberFormatException) {
            responseCode?.let { wlResponse?.onError(ResponseState.Error(numberFormatEx), it) }
        } catch (IOex: IOException) {
            responseCode?.let { wlResponse?.onError(ResponseState.Error(IOex), it) }
        } catch (illegalStateEx: IllegalStateException) {
            responseCode?.let { wlResponse?.onError(ResponseState.Error(illegalStateEx), it) }
        } catch (ex: Exception) {
            responseCode?.let { wlResponse?.onError(ResponseState.Error(ex), it) }
        }
        }


    }
}