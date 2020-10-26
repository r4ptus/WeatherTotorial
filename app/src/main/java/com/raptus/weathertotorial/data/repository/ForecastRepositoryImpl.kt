package com.raptus.weathertotorial.data.repository

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raptus.weathertotorial.data.network.WeatherNetworkDataSource
import com.raptus.weathertotorial.data.network.response.CurrentWeatherResponse
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<CurrentWeatherResponse> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            val response = MutableLiveData<CurrentWeatherResponse>()
            response.postValue(Paper.book().read<CurrentWeatherResponse>("current_weather_response"))
           return@withContext response
       }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            //persist data in the db
            Paper.book().write("current_weather_response",fetchedWeather)
        }
    }
    private suspend fun initWeatherData(){
        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }
    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            "Alzey"
        )
    }
    private fun isFetchCurrentNeeded(lastFetchedTime: org.threeten.bp.ZonedDateTime) : Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinutesAgo)
    }
}