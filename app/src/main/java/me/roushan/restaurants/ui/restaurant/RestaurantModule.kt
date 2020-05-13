package me.roushan.restaurants.ui.restaurant

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.roushan.restaurants.di.ViewModelKey

@Module
abstract class RestaurantModule {

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantActivityViewModel::class)
    abstract fun bindRestaurantActivityViewModel(viewModel: RestaurantActivityViewModel): ViewModel


}