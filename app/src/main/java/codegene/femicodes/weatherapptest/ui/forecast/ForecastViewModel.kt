package codegene.femicodes.weatherapptest.ui.forecast

import androidx.lifecycle.*
import codegene.femicodes.weatherapptest.R
import codegene.femicodes.weatherapptest.local.model.Forecast
import codegene.femicodes.weatherapptest.repository.Event
import codegene.femicodes.weatherapptest.repository.Result
import codegene.femicodes.weatherapptest.repository.WeatherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _forecasts = weatherRepository.observeForecasts().switchMap { filterResult(it) }

    val forecasts: LiveData<List<Forecast>> = _forecasts

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    val empty: LiveData<Boolean> = Transformations.map(_forecasts) {
        it.isEmpty()
    }

    private fun filterResult(tasksResult: Result<List<Forecast>>): LiveData<List<Forecast>> {
        val result = MutableLiveData<List<Forecast>>()

        if (tasksResult is Result.Success) {
            result.value = tasksResult.data
        } else {
            result.value = emptyList()
        }

        return result
    }

    fun fetchForecastByLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            _loading.value = true
            val result = weatherRepository.fetchForecastsByLocation(lat, lon)
            if (result is Result.Error){
                showSnackbarMessage(result.exception.localizedMessage ?: "Something went wrong")
            }
            _loading.value = false
        }
    }

    fun fetchForecastByCity(city: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = weatherRepository.fetchForecasts(city)
            if (result is Result.Error){
                showSnackbarMessage(result.exception.localizedMessage ?: "Something went wrong")
            }
            _loading.value = false
        }

    }

    private fun showSnackbarMessage(message: String) {
        _snackbarText.value = Event(message)
    }
}