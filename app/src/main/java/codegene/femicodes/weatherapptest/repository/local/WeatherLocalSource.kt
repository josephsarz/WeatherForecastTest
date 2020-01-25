package codegene.femicodes.weatherapptest.repository.local

import androidx.lifecycle.LiveData
import codegene.femicodes.weatherapptest.local.model.Forecast
import codegene.femicodes.weatherapptest.repository.Result

interface WeatherLocalSource {

    fun getForecasts(): Result<List<Forecast>>

    fun observeForecasts(): LiveData<Result<List<Forecast>>>

    fun saveForecasts(forecasts: List<Forecast>)

    fun clearData()
}