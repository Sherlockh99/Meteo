package com.sherlock.gb.kotlin.lessons.repository.xdto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("tz_id")
    val tzId: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int,
    @SerializedName("localtime")
    val localtime: String
): Parcelable