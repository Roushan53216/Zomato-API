package me.roushan.restaurants.ui.main.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.item_home_page_restaurant.view.*
import me.roushan.restaurants.R
import me.roushan.restaurants.data.model.Restaurant
import me.roushan.restaurants.helpers.loadImage
import me.roushan.restaurants.ui.base.BaseViewHolder
import me.roushan.restaurants.ui.restaurant.RestaurantsActivity

class RestaurantViewHolder(itemView: View) : BaseViewHolder(itemView) {


    private var restaurant: Restaurant? = null
    val featureImageView = itemView.featureImageView
    val resNameTextView = itemView.resNameTextView
    val ratingBar = itemView.ratingBar
    val votesNumberTextView = itemView.votesNumberTextView

    init {
        itemView.setOnClickListener {view ->
            restaurant!!.id?.let { restaurantId ->
                RestaurantsActivity.getNewIntent(itemView.context as AppCompatActivity, restaurantId).also {
                    view.context.startActivity(it)
                }
            }
        }
    }

    fun bind(restaurant: Restaurant) {
        this.restaurant = restaurant

        featureImageView.loadImage(restaurant!!.featuredImage)
        resNameTextView.text = restaurant!!.name
        ratingBar.rating = restaurant!!.userRating!!.aggregateRating!!.toFloat()
        votesNumberTextView.text = itemView.context.getString(R.string.votes_number).format(restaurant!!.userRating!!.votes!!.toInt())


    }
    companion object {

        private const val LAYOUT_RES = R.layout.item_home_page_restaurant

        fun createViewHolder(parent: ViewGroup): RestaurantViewHolder {
            return RestaurantViewHolder(LayoutInflater.from(parent.context).inflate(LAYOUT_RES, parent, false))
        }
    }
}