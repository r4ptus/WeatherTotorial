package com.raptus.weathertotorial.data.network.response


import com.google.gson.annotations.SerializedName
import com.raptus.weathertotorial.data.db.entity.Current
import com.raptus.weathertotorial.data.db.entity.Daily
import com.raptus.weathertotorial.data.db.entity.Hourly
import com.raptus.weathertotorial.data.db.entity.Minutely

data class CompleteWeatherResponse(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int
)