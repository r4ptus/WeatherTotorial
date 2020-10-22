package com.raptus.weathertotorial.data.db.entity


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val h: Double
)