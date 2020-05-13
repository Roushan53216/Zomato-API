package me.roushan.restaurants.data

import dagger.Module
import dagger.Provides
import me.roushan.restaurants.App
import me.roushan.restaurants.data.network.NetworkModule
import me.roushan.restaurants.data.network.Routes
import me.roushan.restaurants.data.preferences.PreferencesModule
import me.roushan.restaurants.di.AppModule
import me.roushan.restaurants.di.AppScope

@Module(includes = [AppModule::class, NetworkModule::class, PreferencesModule::class])
class DataModule {

    @AppScope
    @Provides
    fun provideAppDataRepository(app: App, apiService: Routes): DataRepository =
        AppDataRepository(app, apiService)


}