package me.roushan.restaurants.ui.restaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import me.roushan.restaurants.R
import me.roushan.restaurants.data.model.ReviewWrapper
import me.roushan.restaurants.databinding.ItemRestaurantReviewBinding
import me.roushan.restaurants.ui.base.BaseViewHolder


class RestaurantReviewViewHolder(var binding: ItemRestaurantReviewBinding) : BaseViewHolder(binding.root) {

    fun bind(reviewWrapper : ReviewWrapper) {
        binding.review = reviewWrapper.review
    }

    companion object {
        private const val LAYOUT_RES = R.layout.item_restaurant_review
        fun createViewHolder(parent: ViewGroup): RestaurantReviewViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            var binding: ItemRestaurantReviewBinding = ItemRestaurantReviewBinding.inflate(layoutInflater,parent,false)
            return RestaurantReviewViewHolder(binding)
        }
    }
}