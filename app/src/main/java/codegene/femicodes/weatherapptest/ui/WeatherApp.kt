package codegene.femicodes.weatherapptest.ui

import codegene.femicodes.weatherapptest.BuildConfig
import codegene.femicodes.weatherapptest.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class WeatherApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}