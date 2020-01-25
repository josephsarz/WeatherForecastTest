package codegene.femicodes.weatherapptest.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import codegene.femicodes.weatherapptest.local.db.WeatherDB
import codegene.femicodes.weatherapptest.local.entity.ForecastEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForecastDaoTest {

    private lateinit var weatherDB: WeatherDB

    @Before
    fun initDb() {
        weatherDB = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            WeatherDB::class.java
        ).build()
    }

    @After
    fun closeDb() {
        weatherDB.close()
    }

    @Test
    fun insertForecastEntitySavesData() {
        val forecastEntityList = listOf<ForecastEntity>(ForecastEntity(1579748400, 804,
            "overcast clouds", 78.55F, 73.69F))
        weatherDB.forecastDao.insertForecasts(forecastEntityList)
        val forecastList = weatherDB.forecastDao.getForecasts()
        assert(forecastList.isNotEmpty())
    }

    @Test
    fun deleteForecastClearsData() {
        val forecastEntityList = listOf<ForecastEntity>(ForecastEntity(1579748400, 804,
            "overcast clouds", 78.55F, 73.69F))
        weatherDB.forecastDao.insertForecasts(forecastEntityList)
        val forecastList = weatherDB.forecastDao.getForecasts()
        assert(forecastList.isNotEmpty())

        weatherDB.forecastDao.clearData()
        assert(weatherDB.forecastDao.getForecasts().isEmpty())

    }


}