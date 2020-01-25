package codegene.femicodes.weatherapptest.di

import android.content.Context
import codegene.femicodes.weatherapptest.local.dao.ForecastDao
import codegene.femicodes.weatherapptest.local.db.WeatherDB
import codegene.femicodes.weatherapptest.remote.ForecastService
import codegene.femicodes.weatherapptest.repository.local.WeatherLocalSource
import codegene.femicodes.weatherapptest.repository.local.WeatherLocalSourceImpl
import codegene.femicodes.weatherapptest.repository.remote.WeatherRemoteSource
import codegene.femicodes.weatherapptest.repository.remote.WeatherRemoteSourceImpl
import codegene.femicodes.weatherapptest.ui.utils.AppConst
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConst.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideForecastService(retrofit: Retrofit): ForecastService {
        return retrofit.create(ForecastService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDB(context: Context): WeatherDB {
        return WeatherDB.getWeatherDb(context)
    }

    @Provides
    @Singleton
    fun provideForecastDao(weatherDB: WeatherDB): ForecastDao {
        return weatherDB.forecastDao
    }

    @Singleton
    @Provides
    fun providesFusedLocation(context: Context) : FusedLocationProviderClient{
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideWeatherRemoteSource(forecastService: ForecastService): WeatherRemoteSource {
        return WeatherRemoteSourceImpl(forecastService)
    }

    @Singleton
    @Provides
    fun provideWeatherLocalSource(forecastDao: ForecastDao): WeatherLocalSource {
        return WeatherLocalSourceImpl(forecastDao)
    }

}