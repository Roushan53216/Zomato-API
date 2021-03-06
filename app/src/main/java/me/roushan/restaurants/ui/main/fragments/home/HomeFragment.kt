package me.roushan.restaurants.ui.main.fragments.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.appbarLayout
import kotlinx.android.synthetic.main.fragment_home.selectedCityIcon
import kotlinx.android.synthetic.main.layout_shimmer_loading_page.*
import me.roushan.restaurants.R
import me.roushan.restaurants.data.model.*
import me.roushan.restaurants.data.network.ENTITY_TYPE_CITY
import me.roushan.restaurants.helpers.hide
import me.roushan.restaurants.helpers.shortToast
import me.roushan.restaurants.helpers.show
import me.roushan.restaurants.ui.main.fragments.HomeBaseFragment
import me.roushan.restaurants.ui.main.fragments.home.restaurant_marker_bottom_sheet_dialog.RestaurantMarkerDialogFragment
import me.roushan.restaurants.ui.restaurant.RestaurantsActivity
import me.roushan.restaurants.utils.CustomMarkerMakerUtils
import me.roushan.restaurants.utils.ScreenUtils
import me.roushan.restaurants.viewmodels.ViewModelProviderFactory
import javax.inject.Inject
import kotlin.math.abs


class HomeFragment : HomeBaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    RestaurantMarkerDialogFragment.RestaurantMarkerInfoDialogListener {


    private var drawnMarkers: MutableList<Marker>? = null
    private lateinit var viewModel: HomeFragmentViewModel
    private var mMap: GoogleMap? = null
    private var locationDetails: LocationDetails? = null
    private var homePageData: HomePageData? = null
    private val homeAdapter by lazy {
        HomeAdapter()
    }

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders
            .of(this, providerFactory)
            .get(HomeFragmentViewModel::class.java)
        viewModel.homePageLiveData.observe(this, HomePageLiveDataObserver())
        viewModel.loadingDataLiveData.observe(this, LoadingLiveDataObserver())

    }

    override fun onNewCitySelected(city: City) {
        super.onNewCitySelected(city)
        viewModel.loadCity("${city.id}", ENTITY_TYPE_CITY)
        showSelectedCity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAppBarOffsetChangedListener()
        initializeTheSwitchToMapButton()

        selectedCityIcon.setOnClickListener {
            searchForCity()
        }

        showSelectedCity()
        initRecycler()
        initSearchAndFilterButton()


        view.postDelayed({
            if (isAdded)
                initMap()
        }, 16 * 200)
    }

    private fun initSearchAndFilterButton() {
        filterResultButton.setOnClickListener {
            // todo
            shortToast("Filter Results")
        }
        searchEditText.setOnClickListener {
            // todo
            shortToast("Not implemented yet sorry :)")
        }
    }

    private fun initializeTheSwitchToMapButton() {
        switchToMapButton.tag = false
        switchToMapButton.setOnClickListener {
            if (it.tag == false) {
                appbarLayout.hide()
                recyclerView.hide()
                if (mMap == null)
                    initMap()
                else
                    setupMap()
            } else {
                appbarLayout.show()
                recyclerView.show()
            }
            it.tag = (it.tag as Boolean).not()
        }
    }


    private fun setAppBarOffsetChangedListener() {
        appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            when {
                abs(verticalOffset) == appBarLayout.totalScrollRange -> {// If collapsed, then do this
                    searchCard.hide()
                    switchToMapButton.hide()
                }
                verticalOffset == 0 -> { // If expanded, then do this
                    searchCard.show()
                    if (canShowSwitchToMapButton()) {
                        switchToMapButton.show()
                    }
                }
                else -> {
                    // Somewhere in between
                    // Do according to your requirement
                }
            }
        })
    }

    private fun canShowSwitchToMapButton(): Boolean = homePageData != null && mMap != null


    private fun initRecycler() {
        recyclerView.apply {
            adapter = homeAdapter
        }
    }

    private fun showSelectedCity() {
        var city: City? = prefs.getSelectedCity()
        selectedCityName!!.text =
            getString(R.string.toobar_city_name).format(city!!.name, city.countryName)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap!!.setOnMarkerClickListener(this)
        drawnMarkers = mutableListOf()

        if (canShowSwitchToMapButton())
            switchToMapButton.show()
    }

    private fun setupMap() {

        if (locationDetails == null)
            return
        removePreviousMarkersIfExists()

        var restaurantsList = collectAllRestaurantsFromCityData()

        restaurantsList.forEach {
            addMarkerToMap(it)
        }

        makeZoomToShowAllMarkers(calcTheLatLngBoundsOfAllRestaurants(restaurantsList))
    }

    private fun collectAllRestaurantsFromCityData(): MutableList<Restaurant> {
        var list: MutableList<Restaurant> = mutableListOf()

        locationDetails!!.bestRatedRestaurant!!.forEach {
            it.restaurant?.let { restaurant -> list.add(restaurant) }
        }

        homePageData?.restaurants?.forEach {
            list.add(it)
        }
        return list
    }

    private fun removePreviousMarkersIfExists() {
        drawnMarkers!!.forEach {
            it.remove()
        }
        drawnMarkers = mutableListOf()
    }

    private fun addMarkerToMap(restaurant: Restaurant?) {
        restaurant?.let { restaurant ->
            restaurant.location?.let { location ->
                val latLng = LatLng(location.latitude, location.longitude)
                drawMarker(latLng, restaurant.featuredImage, restaurant)
            }
        }

    }

    private fun drawMarker(latLng: LatLng, featuredImage: String?, restaurant: Restaurant) {
        CustomMarkerMakerUtils.getMarker(context, featuredImage) {
            val marker = mMap!!.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(it!!))
            )
            marker.tag = restaurant
            drawnMarkers!!.add(marker)
        }
    }

    private fun calcTheLatLngBoundsOfAllRestaurants(restaurants: MutableList<Restaurant>): LatLngBounds {
        val builder = LatLngBounds.Builder()
        restaurants.forEach {
            val latLng = LatLng(it.location!!.latitude, it.location!!.longitude)
            builder.include(latLng)
        }

        return builder.build()
    }

    private fun makeZoomToShowAllMarkers(bounds: LatLngBounds) {


        val width = resources.displayMetrics.widthPixels
        val height = ScreenUtils.dpToPx(context, 300)
        val padding = (width * 0.12).toInt()
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding)

        mMap!!.animateCamera(cu)
    }

    /**
     * Build a list for the [HomeAdapter]
     */
    private fun initAdapter() {
        var list: MutableList<HomeAdapterData> = mutableListOf()

        locationDetails!!.topCuisines?.let { list.add(TopCuisines(it)) }
        locationDetails!!.bestRatedRestaurant?.let { list.add(BestRatedRestaurants(it)) }

        homePageData!!.restaurants.forEach {
            list.add(RestaurantData(it))
        }

        homeAdapter.dataList = list
    }


    private fun initMap() {
        var mapFragment: SupportMapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()
        mapFragment.getMapAsync(this)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker!!.tag is Restaurant) {
            (marker.tag as Restaurant).id?.let { it ->
                //                RestaurantsActivity.getNewIntent(activity as AppCompatActivity, it).also { intent ->
//                    startActivity(intent)
//                }

                RestaurantMarkerDialogFragment.newInstance(it).show(childFragmentManager, "5")
            }
            return true
        }
        return false
    }

    override fun onRestaurantMoreInfoClicked(id: String) {
        RestaurantsActivity.getNewIntent(activity as AppCompatActivity, id).also { intent ->
            startActivity(intent)
        }
    }

    inner class HomePageLiveDataObserver : Observer<HomePageData> {

        override fun onChanged(homePageData: HomePageData) {
            this@HomeFragment.homePageData = homePageData
            this@HomeFragment.locationDetails = homePageData.locationDetails
            initAdapter()

            if (canShowSwitchToMapButton())
                switchToMapButton.show()
        }

    }

    inner class LoadingLiveDataObserver : Observer<Boolean> {

        override fun onChanged(loading: Boolean) {
            if (loading) {
                shimmer_Scroll.show()
                shimmer_layout.startShimmer()
            } else {
                shimmer_Scroll.hide()
                shimmer_layout.stopShimmer()
            }
        }

    }


    companion object {

        fun newInstance() = HomeFragment()
    }

}


