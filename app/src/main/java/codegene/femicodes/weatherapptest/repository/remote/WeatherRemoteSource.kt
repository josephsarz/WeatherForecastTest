package codegene.femicodes.weatherapptest.repository.remote

import codegene.femicodes.weatherapptest.local.model.Forecast
import codegene.femicodes.weatherapptest.repository.Result

interface WeatherRemoteSource {

    suspend fun getForecasts(city: String): Result<List<Forecast>>

    suspend fun getForecastsByLocation(lat: Double, lon: Double): Result<List<Forecast>>
}