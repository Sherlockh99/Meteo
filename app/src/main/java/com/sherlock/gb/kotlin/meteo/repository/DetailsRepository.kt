package com.sherlock.gb.kotlin.meteo.repository

import com.sherlock.gb.kotlin.meteo.viewmodel.DetailsViewModel

interface DetailsRepository {
    fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback)
}