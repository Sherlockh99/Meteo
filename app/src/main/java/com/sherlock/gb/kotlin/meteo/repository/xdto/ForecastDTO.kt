package com.sherlock.gb.kotlin.lessons.repository.xdto


import com.google.gson.annotations.SerializedName

data class ForecastDTO(
    @SerializedName("forecastday")
    val forecastday: List<ForecastdayDTO>
)