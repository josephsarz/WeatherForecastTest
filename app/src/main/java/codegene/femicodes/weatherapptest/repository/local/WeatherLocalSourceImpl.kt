package codegene.femicodes.weatherapptest.repository.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import codegene.femicodes.weatherapptest.domain.mapper.toDomain
import codegene.femicodes.weatherapptest.domain.mapper.toEntity
import codegene.femicodes.weatherapptest.local.dao.ForecastDao
import codegene.femicodes.weatherapptest.local.model.Forecast
import codegene.femicodes.weatherapptest.repository.Result
import javax.inject.Inject


class WeatherLocalSourceImpl @Inject constructor(private val forecastDao: ForecastDao) : WeatherLocalSource{

    override fun getForecasts(): Result<List<Forecast>> {
        return try {
            Result.Success(forecastDao.getForecasts().toDomain())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun observeForecasts(): LiveData<Result<List<Forecast>>> {
        return forecastDao.observeForecasts()
            .distinctUntilChanged()
            .map {
                Result.Success(it.toDomain())
            }
    }

    override fun saveForecasts(forecasts: List<Forecast>) {
        forecastDao.insertForecasts(forecasts.toEntity())
    }

    override fun clearData() {
        forecastDao.clearData()
    }
}