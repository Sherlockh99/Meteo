package com.sherlock.gb.kotlin.meteo.repository

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.sherlock.gb.kotlin.lessons.repository.xdto.WeatherDTO
import com.sherlock.gb.kotlin.meteo.BuildConfig
import com.sherlock.gb.kotlin.meteo.viewmodel.ResponseState
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(private val onServerResponseListener: OnServerResponse,
                    private val onErrorListener: OnServerResponseListener) {
    fun loadWeather(lat: Double, lon: Double){
        val urlText = "https://api.weatherapi.com/v1/forecast.json?q=$lat,$lon&lang=ru"
        val uri = URL(urlText)

        val urlCollection: HttpsURLConnection =
            (uri.openConnection() as HttpsURLConnection).apply {
                connectTimeout = 1000 //сколько ждать подключения; под капотом set
                readTimeout = 1000 //сколько ждать ответа
                addRequestProperty("key",BuildConfig.WEATHER_API_KEY)
            }
        Thread{
            try{
                val responseCode = urlCollection.responseCode

                val serverSide = 500 //диапазон ошибок
                val clientSide = 400..499 //диапазон ошибок
                val responnse = 200..299

                if(responseCode>=serverSide){
                    Handler(Looper.getMainLooper()).post{onErrorListener.onError(ResponseState.ServerSide)}
                }else if(responseCode in clientSide){
                    Handler(Looper.getMainLooper()).post{onErrorListener.onError(ResponseState.ClientSide(responseCode))}
                }else if(responseCode in responnse){
                    val buffer = BufferedReader(InputStreamReader(urlCollection.inputStream))
                    val weatherDTO: WeatherDTO = Gson().fromJson(buffer,WeatherDTO::class.java)
                    Handler(Looper.getMainLooper()).post{onServerResponseListener.onResponse(weatherDTO)}
                }
            }catch (e: Exception){
                Log.e("Error get weather",e.toString())
            }finally {
                urlCollection.disconnect()
            }
        }.start()
    }
}