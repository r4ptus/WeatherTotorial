package com.raptus.weathertotorial.ui.weather.current

import androidx.lifecycle.ViewModel
import com.raptus.weathertotorial.data.provider.UnitProvider
import com.raptus.weathertotorial.data.repository.ForecastRepository
import com.raptus.weathertotorial.internal.UnitSystem
import com.raptus.weathertotorial.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric:Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}