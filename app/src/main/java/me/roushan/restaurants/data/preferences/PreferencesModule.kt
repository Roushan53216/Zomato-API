package me.roushan.restaurants.data.preferences


import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import me.roushan.restaurants.App
import me.roushan.restaurants.di.AppModule
import me.roushan.restaurants.di.AppScope
import javax.inject.Named

@Module(includes = [AppModule::class])
class PreferencesModule {

    @AppScope
    @Provides
    fun provideAppPreferences(app: App, @Named("prefs") gson: Gson): AppSharedPreferences =
        AppPreferences(app, gson)


    @Provides
    @Named("prefs")
    fun provideGson() = Gson()
}