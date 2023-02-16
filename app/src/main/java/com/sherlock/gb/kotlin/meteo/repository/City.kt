package com.sherlock.gb.kotlin.meteo.repository

data class City(val name: String, val lat:Double, val lon:Double)

fun getDefaultCity() = City("London",51.5287276,-0.1716375)