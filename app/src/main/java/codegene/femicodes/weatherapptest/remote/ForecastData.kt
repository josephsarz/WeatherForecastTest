package codegene.femicodes.weatherapptest.remote

import com.google.gson.annotations.SerializedName


data class ForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastData>,
    val message: Double
)

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)

data class ForecastData(
    val dt: Long,
    @SerializedName( "dt_txt")
    val dtTxt: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(
    @SerializedName("grnd_level")
    var grndLevel: Double,
    val humidity: Int,
    val pressure: Double,
    @SerializedName("sea_level")
    val seaLevel: Double,
    val temp: Double,
    @SerializedName("temp_kf")
    val tempKf: Float,
    @SerializedName("temp_max")
    val tempMax: Float,
    @SerializedName("temp_min")
    val tempMin: Float
)

data class Wind(
    val deg: Double,
    val speed: Double
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Coord(
    val lat: Double,
    val lon: Double
)