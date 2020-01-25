package codegene.femicodes.weatherapptest.remote

import retrofit2.http.GET
import retrofit2.http.Query


interface ForecastService {

    @GET("data/2.5/forecast")
    suspend fun getForecasts(
        @Query("q") city: String,
        @Query("units") unit: String,
        @Query("appid") apiKey: String
    ): ForecastResponse

    @GET("data/2.5/forecast")
    suspend fun getForecastsByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unit: String,
        @Query("appid") apiKey: String
    ): ForecastResponse
}