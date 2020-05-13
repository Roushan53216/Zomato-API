package me.roushan.restaurants.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import me.roushan.restaurants.viewmodels.ViewModelProviderFactory

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}
