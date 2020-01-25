package codegene.femicodes.weatherapptest.repository

import androidx.lifecycle.LiveData
import codegene.femicodes.weatherapptest.local.model.Forecast
import codegene.femicodes.weatherapptest.repository.local.WeatherLocalSource

class FakeWeatherLocalSource(var forecastList: MutableList<Forecast>? = mutableListOf()) : WeatherLocalSource {

    override fun getForecasts(): Result<List<Forecast>> {
        forecastList?.let { return Result.Success(ArrayList(it)) }
        return Result.Error(
            Exception("Tasks not found")
        )
    }

    override fun observeForecasts(): LiveData<Result<List<Forecast>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveForecasts(forecasts: List<Forecast>) {
        forecastList?.addAll(forecasts)
    }

    override fun clearData() {
        forecastList?.clear()
    }

}