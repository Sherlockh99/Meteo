package com.sherlock.gb.kotlin.meteo.repository.weather

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.sherlock.gb.kotlin.lessons.repository.xdto.WeatherDTO
import com.sherlock.gb.kotlin.meteo.BuildConfig
import com.sherlock.gb.kotlin.meteo.repository.OnServerResponse
import com.sherlock.gb.kotlin.meteo.repository.OnServerResponseListener
import com.sherlock.gb.kotlin.meteo.viewmodel.ResponseState
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(private val onServerResponseListener: OnServerResponse,
                    private val onErrorListener: OnServerResponseListener
) {
    fun loadWeather(lat: Double, lon: Double){
        val urlText = "$WEATHER_DOMAIN$WEATHER_ENDPOINT?q=$lat,$lon&lang=ru"
        //val urlText = "https://api.weatherapi.com/v1/forecast.json?q=$lat,$lon&lang=ru"
        val uri = URL(urlText)

        val urlCollection: HttpsURLConnection =
            (uri.openConnection() as HttpsURLConnection).apply {
                connectTimeout = 1000 //сколько ждать подключения; под капотом set
                readTimeout = 1000 //сколько ждать ответа
                addRequestProperty(WEATHER_KEY,BuildConfig.WEATHER_API_KEY)
            }
        Thread{
            try{
                val responseCode = urlCollection.responseCode

                val serverSide = 500..599 //диапазон ошибок на стороне сервера
                val clientSide = 400..499 //диапазон ошибок
                val responseOK = 200..299

                when (responseCode) {
                    in serverSide -> {
                        Handler(Looper.getMainLooper()).post{onErrorListener.onError(ResponseState.ServerSide)}
                    }
                    in clientSide -> {
                        Handler(Looper.getMainLooper()).post{onErrorListener.onError(ResponseState.ClientSide(responseCode))}
                    }
                    in responseOK -> {
                        val buffer = BufferedReader(InputStreamReader(urlCollection.inputStream))
                        val weatherDTO: WeatherDTO = Gson().fromJson(buffer,WeatherDTO::class.java)
                        Handler(Looper.getMainLooper()).post{
                            onServerResponseListener.onResponse(weatherDTO)}
                    }
                }
            }catch (e: JsonSyntaxException){
                Log.e("Error get weather",e.toString())
            }finally {
                urlCollection.disconnect()
            }
        }.start()
    }
}