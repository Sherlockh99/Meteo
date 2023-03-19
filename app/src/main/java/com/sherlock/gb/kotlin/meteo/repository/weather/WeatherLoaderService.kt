package com.sherlock.gb.kotlin.meteo.repository.weather

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.sherlock.gb.kotlin.lessons.repository.xdto.WeatherDTO
import com.sherlock.gb.kotlin.meteo.BuildConfig
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherLoaderService(val name: String=""): IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            val lat = it.getDoubleExtra(KEY_BUNDLE_LAT,0.0).toString()
            val lon = it.getDoubleExtra(KEY_BUNDLE_LON,0.0).toString()
            val urlText = "$WEATHER_DOMAIN$WEATHER_ENDPOINT?q=$lat,$lon&lang=ru"
            val uri = URL(urlText)
            val urlCollection: HttpsURLConnection =
                (uri.openConnection() as HttpsURLConnection).apply {
                    connectTimeout = 1000
                    readTimeout = 1000
                    addRequestProperty(WEATHER_KEY, BuildConfig.WEATHER_API_KEY)
                }
            try{
                val headers = urlCollection.headerFields
                val responseCode = urlCollection.responseCode
                val responseMessage = urlCollection.responseMessage

                val serverSide = 500..599 //диапазон ошибок на стороне сервера
                val clientSide = 400..499 //диапазон ошибок на стороне клиента
                val responseOK = 200..299

                when (responseCode) {
                    in serverSide -> {
                    }
                    in clientSide -> {
                    }
                    in responseOK -> {
                        val buffer = BufferedReader(InputStreamReader(urlCollection.inputStream))
                        val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                        val message = Intent(KEY_WAVE_SERVICE_BROADCAST)
                        message.putExtra(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER, weatherDTO)
                        sendBroadcast(message)
                    }
                    else -> {

                    }
                }
            }catch (e: JsonSyntaxException){
                Log.e("Error get weather",e.toString())
            }finally {
                urlCollection.disconnect()
            }
        }
    }
}