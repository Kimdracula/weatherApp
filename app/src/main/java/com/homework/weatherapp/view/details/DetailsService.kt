package com.homework.weatherapp.view.details

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.homework.weatherapp.BuildConfig
import com.homework.weatherapp.model.WeatherDTO
import com.homework.weatherapp.utils.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailsService : IntentService("DetailsService") {

    private var urlConnection: HttpsURLConnection? = null
    private var responseCode: Int? = null
    private val message = Intent(KEY_BROADCAST_INTENT)


    override fun onHandleIntent(p0: Intent?) {
        p0?.let { intent ->
            try {
                val lat = intent.getDoubleExtra(KEY_INTENT_LAT, 0.00)
                val lon = intent.getDoubleExtra(KEY_INTENT_LON, 0.00)
                val url = URL("$SCHEME//$AUTHORITY$QUERY_LAT=$lat&$QUERY_LON=$lon")
                //  val url = URL("http://212.86.114.27/v2/informers?lat=$lat&lon=$lon")

                urlConnection = url.openConnection() as HttpsURLConnection
                urlConnection?.apply {
                    requestMethod = "GET"
                    readTimeout = 10000
                    connectTimeout = 10000
                    addRequestProperty(WEATHER_API_LOGIN, BuildConfig.WEATHER_API_KEY)
                }

                responseCode = urlConnection!!.responseCode

                val reader = BufferedReader(InputStreamReader(urlConnection!!.inputStream))
                val weatherDTO: WeatherDTO = Gson().fromJson(reader, WeatherDTO::class.java)
                onResponse(weatherDTO)

            } catch (IOEx: IOException) {
                responseCode?.let { onError(it) }
            } catch (numberFormatEx: NumberFormatException) {
                responseCode?.let { onError(it) }
            } catch (illegalStateEx: IllegalStateException) {
                responseCode?.let { onError(it) }
            } catch (ex: Exception) {
                responseCode?.let { onError(it) }
            } finally {
                urlConnection?.disconnect()
            }
        }

    }

    private fun onError(it: Int) {
        message.putExtra(KEY_BROADCAST_ERROR_MESSAGE, it)
        LocalBroadcastManager.getInstance(this).sendBroadcast(message)
    }

    private fun onResponse(weatherDTO: WeatherDTO) {
        message.putExtra(KEY_BROADCAST_MESSAGE, weatherDTO)
        LocalBroadcastManager.getInstance(this).sendBroadcast(message)
    }
}