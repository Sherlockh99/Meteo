package com.sherlock.gb.kotlin.lessons.repository.xdto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConditionDTO(
    @SerializedName("code")
    val code: Int,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("text")
    val text: String
): Parcelable