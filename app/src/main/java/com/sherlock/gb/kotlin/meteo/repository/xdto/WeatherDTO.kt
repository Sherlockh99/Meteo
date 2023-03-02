package com.sherlock.gb.kotlin.lessons.repository.xdto


import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("location")
    val location: LocationDTO,
    @SerializedName("current")
    val current: CurrentDTO,
    @SerializedName("forecast")
    val forecast: ForecastDTO
)