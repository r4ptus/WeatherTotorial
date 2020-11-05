package com.raptus.weathertotorial.data.provider

import com.raptus.weathertotorial.data.network.response.CurrentWeatherResponse

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocationString: CurrentWeatherResponse):Boolean
    suspend fun getPreferredLocationString(): String
}