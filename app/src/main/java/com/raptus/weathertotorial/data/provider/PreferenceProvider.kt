package com.raptus.weathertotorial.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

abstract class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext

    val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
}