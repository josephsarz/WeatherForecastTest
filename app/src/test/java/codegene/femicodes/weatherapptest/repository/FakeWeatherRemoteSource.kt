package codegene.femicodes.weatherapptest.repository

import codegene.femicodes.weatherapptest.BuildConfig
import codegene.femicodes.weatherapptest.local.model.Forecast
import codegene.femicodes.weatherapptest.repository.remote.WeatherRemoteSource
import codegene.femicodes.weatherapptest.ui.utils.AppConst

class FakeWeatherRemoteSource(var forecastList: MutableList<Forecast>? = mutableListOf()) : WeatherRemoteSource{

    override suspend fun getForecasts(city: String): Result<List<Forecast>> {
        forecastList?.let { return Result.Success(ArrayList(it)) }
        return Result.Error(Exception("City not found"))
    }

    override suspend fun getForecastsByLocation(lat: Double, lon: Double): Result<List<Forecast>> {
        forecastList?.let { return Result.Success(ArrayList(it)) }
        return Result.Error(Exception("City not found"))
    }


}