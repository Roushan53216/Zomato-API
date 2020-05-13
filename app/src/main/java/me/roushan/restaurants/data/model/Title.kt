package me.roushan.restaurants.data.model

import com.google.gson.annotations.SerializedName

data class Title(

    @field:SerializedName("text")
    var text: String? = null

)