package com.sherlock.gb.kotlin.meteo.viewmodel

sealed class ResponseState {
    object ServerSide : ResponseState()
    data class ClientSide(val codeError: Int) : ResponseState()
}