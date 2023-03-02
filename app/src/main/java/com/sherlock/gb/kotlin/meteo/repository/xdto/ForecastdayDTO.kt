package com.sherlock.gb.kotlin.lessons.repository.xdto


import com.google.gson.annotations.SerializedName

data class ForecastdayDTO(
    @SerializedName("date")
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    @SerializedName("day")
    val day: DayDTO,
    @SerializedName("astro")
    val astro: AstroDTO,
    @SerializedName("hour")
    val hour: List<HourDTO>
)