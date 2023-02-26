package com.sherlock.gb.kotlin.meteo.ui.main

import com.sherlock.gb.kotlin.meteo.repository.Weather

sealed class AppState {
    object Loading : AppState()
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
}