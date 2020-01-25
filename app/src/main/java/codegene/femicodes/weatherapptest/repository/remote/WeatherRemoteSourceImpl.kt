package codegene.femicodes.weatherapptest.repository.remote

import codegene.femicodes.weatherapptest.BuildConfig
import codegene.femicodes.weatherapptest.domain.mapper.toDomain
import codegene.femicodes.weatherapptest.local.model.Forecast
import codegene.femicodes.weatherapptest.remote.ForecastService
import codegene.femicodes.weatherapptest.repository.Result
import codegene.femicodes.weatherapptest.ui.utils.AppConst
import javax.inject.Inject


class WeatherRemoteSourceImpl @Inject constructor(private val forecastService: ForecastService) : WeatherRemoteSource {

    override suspend fun getForecasts(city: String): Result<List<Forecast>> {
        return try {
            forecastService.getForecasts(
                city,
                AppConst.TEMP_UNIT,
                BuildConfig.WeatherApiKey
            ).list.toDomain().let {
                Result.Success(it)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getForecastsByLocation(lat: Double, lon: Double): Result<List<Forecast>> {
        return try {
            forecastService.getForecastsByLocation(
                lat,
                lon,
                AppConst.TEMP_UNIT,
                BuildConfig.WeatherApiKey
            ).list.toDomain().let {
                Result.Success(it)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


}