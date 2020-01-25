package codegene.femicodes.weatherapptest.di

import android.content.Context
import codegene.femicodes.weatherapptest.ui.WeatherApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class, DataModule::class, ForecastModule::class
    ]
)
interface AppComponent : AndroidInjector<WeatherApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
