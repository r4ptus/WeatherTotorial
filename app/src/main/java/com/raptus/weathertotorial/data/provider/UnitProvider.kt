package com.raptus.weathertotorial.data.provider

import com.raptus.weathertotorial.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem() : UnitSystem
}