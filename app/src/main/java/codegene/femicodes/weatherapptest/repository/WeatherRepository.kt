package codegene.femicodes.weatherapptest.repository

import androidx.lifecycle.LiveData
import codegene.femicodes.weatherapptest.local.model.Forecast
import codegene.femicodes.weatherapptest.repository.local.WeatherLocalSource
import codegene.femicodes.weatherapptest.repository.local.WeatherLocalSourceImpl
import codegene.femicodes.weatherapptest.repository.remote.WeatherRemoteSource
import codegene.femicodes.weatherapptest.repository.remote.WeatherRemoteSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherRemoteSource: WeatherRemoteSource,
    private val weatherLocalSource: WeatherLocalSource
) {

    suspend fun fetchForecasts(city: String): Result<List<Forecast>> {
        return withContext(Dispatchers.IO) {
            weatherRemoteSource.getForecasts(city).also {
                if (it is Result.Success) {
                    launch {
                        clearData()
                        weatherLocalSource.saveForecasts(it.data)
                    }

                }
                if (it is Result.Error) {
                    clearData()
                    weatherLocalSource.saveForecasts(emptyList())
                }
            }
        }
    }

    suspend fun fetchForecastsByLocation(
        lat: Double,
        lon: Double
    ): Result<List<Forecast>> {
        return withContext(Dispatchers.IO) {
            weatherRemoteSource.getForecastsByLocation(lat, lon).also {
                if (it is Result.Success) {
                    clearData()
                    weatherLocalSource.saveForecasts(it.data)
                }
                if (it is Result.Error) {
                    clearData()
                    weatherLocalSource.saveForecasts(emptyList())
                }
            }
        }
    }

    fun clearData(){
        weatherLocalSource.clearData()
    }


    fun observeForecasts(): LiveData<Result<List<Forecast>>> = weatherLocalSource.observeForecasts()

}