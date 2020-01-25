package codegene.femicodes.weatherapptest.ui.forecast

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import codegene.femicodes.weatherapptest.R
import codegene.femicodes.weatherapptest.databinding.FragmentHomeBinding
import codegene.femicodes.weatherapptest.ui.utils.AppConst.Companion.PERMISSION_ID
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ForecastFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val forecastViewModel: ForecastViewModel by viewModels { viewModelFactory }
    @Inject
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    private lateinit var binding: FragmentHomeBinding
    private var searchView: SearchView? = null
    var longitude: Double = 0.0
    var latitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentHomeBinding.inflate(inflater, container, false)
            .also { binding = it }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter =
            ForecastsAdapter()
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = forecastViewModel
            rvForecasts.adapter = adapter
        }

        getLastLocation()
        setupSnackbar()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)

        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {

            searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

            val onQueryChange: (String) -> Unit = debounce(
                300L,
                viewLifecycleOwner.lifecycleScope,
                forecastViewModel::fetchForecastByCity
            )
            searchView?.queryHint = "Enter a city"
            searchView?.onTextChange(onQueryChange)

        }
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun setupSnackbar() {
        view?.setupSnackbar(this, forecastViewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions(context!!)) {
            if (isLocationEnabled(activity!!)) {
                mFusedLocationClient?.lastLocation?.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        longitude = location.longitude
                        latitude = location.latitude
                        forecastViewModel.fetchForecastByLocation(latitude, longitude)
                    }
                }
            } else {

                Toast.makeText(context, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions(activity!!)
        }
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        mFusedLocationClient?.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )


    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            latitude = mLastLocation.latitude
            longitude = mLastLocation.longitude
            forecastViewModel.fetchForecastByLocation(latitude, longitude)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }


}
