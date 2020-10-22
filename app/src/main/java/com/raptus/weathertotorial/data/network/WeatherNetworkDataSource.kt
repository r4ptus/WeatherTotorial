package com.raptus.weathertotorial.data.network

import androidx.lifecycle.LiveData
import com.raptus.weathertotorial.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String
    )
}