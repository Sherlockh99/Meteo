package com.sherlock.gb.kotlin.meteo.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(val name: String, val lat:Double, val lon:Double): Parcelable

fun getDefaultCity() = City("London",51.5287276,-0.1716375)