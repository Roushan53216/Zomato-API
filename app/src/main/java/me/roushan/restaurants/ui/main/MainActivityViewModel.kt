package me.roushan.restaurants.ui.main


import androidx.lifecycle.MutableLiveData
import me.roushan.restaurants.data.DataRepository
import me.roushan.restaurants.data.model.LocationDetails
import me.roushan.restaurants.helpers.plus
import me.roushan.restaurants.ui.base.BaseViewModel
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(private var dataRepository: DataRepository) :
    BaseViewModel() {


    var locationDetailLiveData: MutableLiveData<LocationDetails> = MutableLiveData()


    fun getLocationDetails(entityId: String, entityType: String): MutableLiveData<LocationDetails> {
        locationDetailLiveData.value?.let {
            return locationDetailLiveData
        }
        loadCity(entityId, entityType)

        return locationDetailLiveData
    }


    fun loadCity(entityId: String, entityType: String) {
        compositeDisposable + dataRepository
            .locationDetails(entityId, entityType)
            .subscribe({
                locationDetailLiveData.postValue(it)
            }, {
                it.cause
            })
    }


}