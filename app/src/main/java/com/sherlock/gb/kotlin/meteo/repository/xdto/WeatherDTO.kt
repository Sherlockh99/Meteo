package com.sherlock.gb.kotlin.lessons.repository.xdto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDTO(
    @SerializedName("location")
    val location: LocationDTO,
    @SerializedName("current")
    val current: CurrentDTO,
    @SerializedName("forecast")
    val forecast: ForecastDTO
): Parcelable