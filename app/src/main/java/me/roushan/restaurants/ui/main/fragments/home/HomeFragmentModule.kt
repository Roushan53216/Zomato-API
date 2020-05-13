package me.roushan.restaurants.ui.main.fragments.home

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import me.roushan.restaurants.di.FragmentScope
import me.roushan.restaurants.di.ViewModelKey

@Module
abstract class HomeFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): HomeFragment


    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentViewModel::class)
    abstract fun bindMainHomeFragmentViewModel(viewModel: HomeFragmentViewModel): ViewModel

}