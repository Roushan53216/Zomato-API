package me.roushan.restaurants.data.model

import com.google.gson.annotations.SerializedName

data class HasMenuStatus(

    @field:SerializedName("delivery")
    var delivery: Long = 0,
    @field:SerializedName("takeaway")
    var takeaway: Long = 0
)
