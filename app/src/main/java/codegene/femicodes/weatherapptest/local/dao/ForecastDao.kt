package codegene.femicodes.weatherapptest.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import codegene.femicodes.weatherapptest.local.entity.ForecastEntity

@Dao
interface ForecastDao {

    @Insert(onConflict = REPLACE)
    fun insertForecasts(forecasts: List<ForecastEntity>): Array<Long>

    @Query("DELETE FROM forecasts")
    fun clearData()

    @Query("SELECT * FROM forecasts")
    fun observeForecasts(): LiveData<List<ForecastEntity>>

    @Query("SELECT * FROM forecasts")
    fun getForecasts(): List<ForecastEntity>
}