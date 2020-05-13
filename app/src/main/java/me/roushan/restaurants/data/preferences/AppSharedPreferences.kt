package me.roushan.restaurants.data.preferences

import androidx.lifecycle.MutableLiveData
import me.roushan.restaurants.data.model.City



interface AppSharedPreferences {

    fun getSelectedCity(): City

    val observableSelectedCity : MutableLiveData<City>

    fun putSelectedCity(city: City)

    companion object{
        val NONE_SELECTED_CITY = City()
    }
}
