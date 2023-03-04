package com.sherlock.gb.kotlin.meteo.repository

import com.sherlock.gb.kotlin.meteo.viewmodel.ResponseState

interface OnServerResponseListener {
    fun onError(error: ResponseState)
}