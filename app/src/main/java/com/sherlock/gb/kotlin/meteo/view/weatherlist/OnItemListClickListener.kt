package com.sherlock.gb.kotlin.meteo.view.weatherlist

import com.sherlock.gb.kotlin.meteo.repository.Weather

interface OnItemListClickListener {
    fun onItemClick(weather: Weather)
}