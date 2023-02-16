package com.sherlock.gb.kotlin.meteo.repository

interface Repository {
    fun getWeatherFromServer():Weather
    fun getWeatherFromLocalStorage():Weather
}