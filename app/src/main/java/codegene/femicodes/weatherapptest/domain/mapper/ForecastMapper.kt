package codegene.femicodes.weatherapptest.domain.mapper

import codegene.femicodes.weatherapptest.local.entity.ForecastEntity
import codegene.femicodes.weatherapptest.local.model.Forecast
import codegene.femicodes.weatherapptest.remote.ForecastData

@JvmName("ForecastDataToDomain")
fun List<ForecastData>.toDomain(): List<Forecast> = map {
    Forecast(
        dt = it.dt,
        weatherId = it.weather.firstOrNull()?.id ?: 0,
        weatherDesc = it.weather.firstOrNull()?.description ?: "",
        tempMax = it.main.tempMax,
        tempMin = it.main.tempMin
    )
}

@JvmName("ForecastEntityToDomain")
fun List<ForecastEntity>.toDomain(): List<Forecast> = map {
    Forecast(
        dt = it.dt,
        weatherId = it.weatherId,
        weatherDesc = it.weatherDesc,
        tempMax = it.tempMax,
        tempMin = it.tempMin
    )
}

@JvmName("ForecastDomainToEntity")
fun List<Forecast>.toEntity(): List<ForecastEntity> = map {
    ForecastEntity(
        dt = it.dt,
        weatherId = it.weatherId,
        weatherDesc = it.weatherDesc,
        tempMax = it.tempMax,
        tempMin = it.tempMin
    )
}