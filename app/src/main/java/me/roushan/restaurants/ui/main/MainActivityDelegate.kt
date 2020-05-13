package me.roushan.restaurants.ui.main

import androidx.lifecycle.MutableLiveData
import me.roushan.restaurants.data.model.City
import me.roushan.restaurants.data.model.LocationDetails

interface MainActivityDelegate {

    fun onNewCitySelected(city: City)

    fun onChangeSelectedCity(city: City)

    fun getLocationDetails() : MutableLiveData<LocationDetails>

}
