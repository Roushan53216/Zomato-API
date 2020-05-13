package me.roushan.restaurants.ui.main.fragments.search

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.roushan.restaurants.di.FragmentScope


@Module
abstract class SearchFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun contributeSearchFragment2(): SearchFragment
}