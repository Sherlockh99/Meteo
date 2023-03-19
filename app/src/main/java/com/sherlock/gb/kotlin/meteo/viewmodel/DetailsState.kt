package com.sherlock.gb.kotlin.meteo.viewmodel

import com.sherlock.gb.kotlin.meteo.repository.weather.Weather

sealed class DetailsState {
    object Loading : DetailsState()
    data class Success(val weather: Weather) : DetailsState()
    data class Error(val error: Throwable) : DetailsState()
}