package com.sherlock.gb.kotlin.lessons.repository.xdto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastDTO(
    @SerializedName("forecastday")
    val forecastday: List<ForecastdayDTO>
): Parcelable