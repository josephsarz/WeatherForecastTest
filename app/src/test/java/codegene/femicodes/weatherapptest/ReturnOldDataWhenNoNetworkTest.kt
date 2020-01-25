package codegene.femicodes.weatherapptest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import codegene.femicodes.weatherapptest.local.dao.ForecastDao
import codegene.femicodes.weatherapptest.local.db.WeatherDB
import codegene.femicodes.weatherapptest.local.entity.ForecastEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ReturnOldDataWhenNoNetworkTest {

    private val sampleList: List<ForecastEntity> = listOf(
        ForecastEntity(1579748400, 804, "overcast clouds", 78.55F, 73.69F),
        ForecastEntity(1579748401, 805, "overcast clouds", 78.55F, 73.69F),
        ForecastEntity(1579748402, 806, "overcast clouds", 78.55F, 73.69F),
        ForecastEntity(1579748403, 807, "overcast clouds", 78.55F, 73.69F)
    )
    private lateinit var forecastDao: ForecastDao
    private lateinit var db: WeatherDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, WeatherDB::class.java
        )
            .allowMainThreadQueries()
            .build()
        forecastDao = db.forecastDao

        forecastDao.insertForecasts(sampleList)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun should_ReturnOldData_WhenThereIsNoNetwork() {

        val forecastList = forecastDao.getForecasts()
        assert(forecastList.isNotEmpty())
    }
}