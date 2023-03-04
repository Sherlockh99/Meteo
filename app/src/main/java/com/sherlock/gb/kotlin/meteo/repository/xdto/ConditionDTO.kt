package com.sherlock.gb.kotlin.lessons.repository.xdto


import com.google.gson.annotations.SerializedName

data class ConditionDTO(
    @SerializedName("code")
    val code: Int,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("text")
    val text: String
)