package me.roushan.restaurants.ui.restaurant

import androidx.lifecycle.MutableLiveData
import me.roushan.restaurants.data.DataRepository
import me.roushan.restaurants.data.model.Restaurant
import me.roushan.restaurants.helpers.plus
import me.roushan.restaurants.ui.base.BaseViewModel
import javax.inject.Inject

class RestaurantActivityViewModel @Inject constructor(private var dataRepository: DataRepository) : BaseViewModel() {

    var restaurantLiveData: MutableLiveData<Restaurant> = MutableLiveData()

    fun loadRestaurant(resId: String) {
        loadingDataLiveData.postValue(true)
        compositeDisposable + dataRepository
            .restaurant(resId)
            .subscribe({
                loadingDataLiveData.postValue(false)
                restaurantLiveData.postValue(it!!)
            }, {
                loadingDataLiveData.postValue(false)
                it.cause
            })
    }
}