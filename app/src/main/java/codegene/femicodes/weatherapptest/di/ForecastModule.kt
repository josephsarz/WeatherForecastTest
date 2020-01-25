package codegene.femicodes.weatherapptest.di

import androidx.lifecycle.ViewModel
import codegene.femicodes.weatherapptest.ui.forecast.ForecastFragment
import codegene.femicodes.weatherapptest.ui.forecast.ForecastViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ForecastModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun forecastsFragment(): ForecastFragment

    @Binds
    @IntoMap
    @ViewModelKey(ForecastViewModel::class)
    abstract fun bindViewModel(viewmodel: ForecastViewModel): ViewModel
}