package me.roushan.restaurants.di


import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.roushan.restaurants.App
import me.roushan.restaurants.data.DataModule
import me.roushan.restaurants.data.network.NetworkModule
import me.roushan.restaurants.data.preferences.PreferencesModule


@AppScope
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    DataModule::class,
    ViewModelFactoryModule::class,
    PreferencesModule::class,
    ActivityBindingModule::class,
    AndroidSupportInjectionModule::class,
    AndroidInjectionModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {

        fun create(appModule: AppModule): AppComponent
    }

}