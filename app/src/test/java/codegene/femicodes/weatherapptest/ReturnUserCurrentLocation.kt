package codegene.femicodes.weatherapptest

import android.location.Location
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class ReturnUserCurrentLocation {

    val fakeLocation =  Location("gps")

    @Before
    fun createDb() {
        fakeLocation.longitude = 0.0
        fakeLocation.latitude = 0.0
    }

    @Test
    fun testLocationReceived(){
        val location = Location("gps")
        location.longitude = 0.0
        location.latitude = 0.0

       // assertThat(fakeLocation, `is`(location))
    }
}