package com.sherlock.gb.kotlin.meteo.repository

import com.google.gson.Gson
import com.sherlock.gb.kotlin.lessons.repository.xdto.WeatherDTO
import com.sherlock.gb.kotlin.meteo.BuildConfig
import com.sherlock.gb.kotlin.meteo.repository.weather.WEATHER_DOMAIN
import com.sherlock.gb.kotlin.meteo.repository.weather.WEATHER_ENDPOINT
import com.sherlock.gb.kotlin.meteo.repository.weather.WEATHER_KEY
import com.sherlock.gb.kotlin.meteo.repository.weather.WeatherUtils.Companion.convertDtoToModel
import com.sherlock.gb.kotlin.meteo.viewmodel.DetailsViewModel
import okhttp3.OkHttpClient
import okhttp3.Request

class DetailsRepositoryOkHttpImpl:DetailsRepository {
    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {

        val client = OkHttpClient()
        val builder = Request.Builder()

        builder.addHeader(WEATHER_KEY, BuildConfig.WEATHER_API_KEY)
        builder.url("$WEATHER_DOMAIN$WEATHER_ENDPOINT?q=${city.lat},${city.lon}&lang=ru")
        val request = builder.build()
        val call = client.newCall(request)
        Thread {
            val response = call.execute() //выполнить здесь и сейчас
            if(response.isSuccessful){
                val weatherDTO: WeatherDTO = Gson().fromJson(response.body()?.string(), WeatherDTO::class.java)
                val weather = convertDtoToModel(weatherDTO)
                callback.onResponse(weather)
            }else{
                //TODO HW
            }
        }.start()
    }
}