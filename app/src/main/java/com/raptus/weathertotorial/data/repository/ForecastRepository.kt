package com.raptus.weathertotorial.data.repository

import androidx.lifecycle.LiveData
import com.raptus.weathertotorial.data.network.response.CurrentWeatherResponse

interface ForecastRepository {

    suspend fun getCurrentWeather(metric: Boolean): LiveData<CurrentWeatherResponse>
}