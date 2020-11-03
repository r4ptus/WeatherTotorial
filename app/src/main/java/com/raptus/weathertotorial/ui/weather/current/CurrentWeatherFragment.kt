package com.raptus.weathertotorial.ui.weather.current

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.raptus.weathertotorial.R
import com.raptus.weathertotorial.data.db.entity.FeelsLike
import com.raptus.weathertotorial.data.network.ConnectivityInterceptorImpl
import com.raptus.weathertotorial.data.network.OpenWeatherApiService
import com.raptus.weathertotorial.data.network.WeatherNetworkDataSourceImpl
import com.raptus.weathertotorial.databinding.CurrentWeatherFragmentBinding
import com.raptus.weathertotorial.internal.glide.GlideApp
import com.raptus.weathertotorial.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(),KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return CurrentWeatherFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if(it == null) return@Observer

            group_loading.visibility = View.GONE
            updateLocation(it.name)
            updateDateToToday()
            updateTemperatures(it.main.temp,it.main.feelsLike)
            updateCondition(it.weather[0].description)
            updateWind(it.wind.speed)
            updateVisibility(it.visibility)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("https://openweathermap.org/img/wn/${it.weather[0].icon}@2x.png")
                .into(imageView_condition_icon)
        })
    }

    private fun updateLocation(location: String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }
    private fun updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }
    private fun updateTemperatures(temperature: Double, feelsLike: Double){
        val unitAbbrevation = if (viewModel.isMetric) "°C" else "°F"

        textView_temperature.text = "%.2f".format(if(viewModel.isMetric) temperature - 273.15 else ((temperature - 273.15) * 9/5 +32)) + "$unitAbbrevation"
        textView_feels_like_temperature.text = "feels like %.2f".format(if(viewModel.isMetric) feelsLike - 273.15 else ((feelsLike - 273.15) * 9/5 +32)) +"$unitAbbrevation"
    }
    private fun updateCondition(condition: String){
        textView_condition.text = condition
    }
    private fun updateWind(speed: Double){
        textView_wind.text = "Wind: $speed m/s"
    }
    private fun updateVisibility(vis: Int){
        textView_visibility.text = "Visibility: ${vis/1000} km"
    }
}