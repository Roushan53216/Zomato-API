package me.roushan.restaurants.ui.main.fragments.home.restaurant_marker_bottom_sheet_dialog

import androidx.lifecycle.MutableLiveData
import me.roushan.restaurants.data.DataRepository
import me.roushan.restaurants.data.model.Restaurant
import me.roushan.restaurants.helpers.plus
import me.roushan.restaurants.ui.base.BaseViewModel
import javax.inject.Inject

class RestaurantMarkerBottomSheetDialogViewModel @Inject constructor(private var dataRepository: DataRepository) :BaseViewModel() {


    val restaurantLiveData : MutableLiveData<Restaurant> = MutableLiveData()


    fun loadRestaurant(resId :String){
        loadingDataLiveData.postValue(true)
        compositeDisposable + dataRepository.restaurant(resId).subscribe(
            {
                restaurantLiveData.postValue(it)
                loadingDataLiveData.postValue(false)
            },{
                loadingDataLiveData.postValue(false)
            }
        )

    }


}