package com.sherlock.gb.kotlin.meteo.repository

import com.sherlock.gb.kotlin.lessons.repository.xdto.WeatherDTO

fun interface OnServerResponse {
    fun onResponse(weatherDTO: WeatherDTO)
}