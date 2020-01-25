package codegene.femicodes.weatherapptest.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecasts")
data class ForecastEntity(
    @PrimaryKey
    val dt: Long,
    @ColumnInfo(name = "weather_id")
    val weatherId: Int,
    @ColumnInfo(name = "weather_desc")
    val weatherDesc: String,
    @ColumnInfo(name = "temp_max")
    val tempMax: Float,
    @ColumnInfo(name = "temp_min")
    val tempMin: Float
)