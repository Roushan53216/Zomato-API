package me.roushan.restaurants.data.model

import com.google.gson.annotations.SerializedName

data class RestaurantWrapper(

    @field:SerializedName("restaurant")
    var restaurant: Restaurant? = null

)