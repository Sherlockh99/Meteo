package com.sherlock.gb.kotlin.meteo.view.weatherlist

import com.sherlock.gb.kotlin.meteo.repository.weather.Weather

interface OnItemListClickListener {
    fun onItemClick(weather: Weather)
}