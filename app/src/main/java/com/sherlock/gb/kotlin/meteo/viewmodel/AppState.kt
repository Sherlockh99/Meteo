package com.sherlock.gb.kotlin.meteo.viewmodel

import com.sherlock.gb.kotlin.meteo.repository.Weather

sealed class AppState {
    object Loading : AppState()
    data class Success(val weatherList: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
}