package com.sherlock.gb.kotlin.meteo.repository

import com.sherlock.gb.kotlin.meteo.repository.weather.Weather

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWorldWeatherFromLocalStorage():List<Weather>
    fun getRussianWeatherFromLocalStorage():List<Weather>
}