package me.roushan.restaurants.data.model

import com.google.gson.annotations.SerializedName

data class PhotoWrapper(

    @field:SerializedName("photo")
    var photo: Photo? = null
)