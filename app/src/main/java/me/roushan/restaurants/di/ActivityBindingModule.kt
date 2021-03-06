package me.roushan.restaurants.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.roushan.restaurants.ui.main.MainActivity
import me.roushan.restaurants.ui.main.MainModule
import me.roushan.restaurants.ui.main.fragments.home.HomeFragmentModule
import me.roushan.restaurants.ui.main.fragments.home.restaurant_marker_bottom_sheet_dialog.RestInfoBottomSheetModule
import me.roushan.restaurants.ui.main.fragments.search.SearchFragmentModule
import me.roushan.restaurants.ui.restaurant.RestaurantModule
import me.roushan.restaurants.ui.restaurant.RestaurantsActivity
import me.roushan.restaurants.ui.search_city.SearchCityActivity
import me.roushan.restaurants.ui.search_city.SearchModule

@Module
@Suppress("UNUSED")
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainModule::class,
            //fragments scope ->
            HomeFragmentModule::class,
            SearchFragmentModule::class,

            //child fragments scope ->
            RestInfoBottomSheetModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun contributeSearchCity(): SearchCityActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = [RestaurantModule::class])
    abstract fun contributeRestaurantsActivity(): RestaurantsActivity


}
