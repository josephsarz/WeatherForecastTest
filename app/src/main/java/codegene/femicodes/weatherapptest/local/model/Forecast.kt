package codegene.femicodes.weatherapptest.local.model

import codegene.femicodes.weatherapptest.ui.utils.formatDate
import codegene.femicodes.weatherapptest.ui.utils.getWeatherDrawable


data class Forecast(
    val dt: Long,
    val weatherId: Int,
    val weatherDesc: String,
    val tempMax: Float,
    val tempMin: Float
) {

    fun getWeatherIcon(): Int {
        return getWeatherDrawable(weatherId)
    }

    fun getReadableDate(): String {
        return formatDate(dt)
    }
}