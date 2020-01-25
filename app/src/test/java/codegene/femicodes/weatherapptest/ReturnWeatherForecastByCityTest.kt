package codegene.femicodes.weatherapptest

import codegene.femicodes.weatherapptest.local.model.Forecast
import codegene.femicodes.weatherapptest.repository.FakeWeatherLocalSource
import codegene.femicodes.weatherapptest.repository.FakeWeatherRemoteSource
import codegene.femicodes.weatherapptest.repository.Result
import codegene.femicodes.weatherapptest.repository.WeatherRepository
import codegene.femicodes.weatherapptest.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ReturnWeatherForecastByCityTest {

    private val forecast1 = Forecast(1579748400, 804, "overcast clouds", 78.55F, 73.69F)
    private val forecast2 = Forecast(1579748401, 805, "overcast clouds", 78.55F, 73.69F)
    private val forecast3 = Forecast(1579748402, 806, "overcast clouds", 78.55F, 73.69F)
    private val forecast4 = Forecast(1579748403, 807, "overcast clouds", 78.55F, 73.69F)

    private val remoteForecast = listOf(forecast1, forecast2, forecast3, forecast4)
    private val localForecast = listOf(forecast1, forecast2, forecast3, forecast4)
    private val cityList = listOf("lagos", "paris", "london")

    private lateinit var weatherRemoteSource: FakeWeatherRemoteSource
    private lateinit var weatherLocalSource: FakeWeatherLocalSource

    // Class under test
    private lateinit var weatherRepository: WeatherRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository(){
        weatherRemoteSource = FakeWeatherRemoteSource(remoteForecast.toMutableList())
        weatherLocalSource = FakeWeatherLocalSource(remoteForecast.toMutableList())

        // Get a reference to the class under test
        weatherRepository = WeatherRepository(
                weatherRemoteSource, weatherLocalSource
        )

    }

    @Test
    fun should_ReturnWeatherForecast_WhenCityIsEntered() = mainCoroutineRule.runBlockingTest {

        // When a random city is entered, forecasts are requested from the weather repository
        val city = cityList.random()
        val tasks = weatherRepository.fetchForecasts(city) as Result.Success

        // Then forecasts are loaded from the remote data source
        Assert.assertThat(tasks.data, IsEqual(remoteForecast))
    }
}