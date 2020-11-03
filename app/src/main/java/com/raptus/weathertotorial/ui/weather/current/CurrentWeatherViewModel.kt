package com.raptus.weathertotorial.ui.weather.current

import androidx.lifecycle.ViewModel
import com.raptus.weathertotorial.data.repository.ForecastRepository
import com.raptus.weathertotorial.internal.UnitSystem
import com.raptus.weathertotorial.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC

    val isMetric:Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}