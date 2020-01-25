package codegene.femicodes.weatherapptest.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import codegene.femicodes.weatherapptest.local.dao.ForecastDao
import codegene.femicodes.weatherapptest.local.entity.ForecastEntity

@Database(entities = [ForecastEntity::class], version = 1, exportSchema = false)
abstract class WeatherDB : RoomDatabase() {

    abstract val forecastDao: ForecastDao

    companion object {

        @Volatile
        private var INSTANCE: WeatherDB? = null

        fun getImWeatherDb(context: Context): WeatherDB {
            return Room.inMemoryDatabaseBuilder(context, WeatherDB::class.java)
                .allowMainThreadQueries()
                .build()
        }

        fun getWeatherDb(context: Context): WeatherDB {

            val weatherInstance = INSTANCE
            if (weatherInstance != null) {
                return weatherInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDB::class.java, "weather_db"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}