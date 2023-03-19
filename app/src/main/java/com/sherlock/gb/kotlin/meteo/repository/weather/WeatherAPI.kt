package com.sherlock.gb.kotlin.meteo.repository.weather

import com.sherlock.gb.kotlin.lessons.repository.xdto.WeatherDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface WeatherAPI {
    @GET(WEATHER_ENDPOINT) //only endpoint
    fun getWeather(@Header(WEATHER_KEY)apikey:String,
                   @Query(WEATHER_QUERY)query:String,
                    @Query(WEATHER_LANG)lang:String
    ): Call<WeatherDTO>
}