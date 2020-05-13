package me.roushan.restaurants

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import me.roushan.restaurants.di.AppComponent
import me.roushan.restaurants.di.AppModule
import me.roushan.restaurants.di.DaggerAppComponent


class App : DaggerApplication(){
    private lateinit var component: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }


    override fun onCreate() {
        component = DaggerAppComponent.factory().create(AppModule(this))
        component.inject(this)
        super.onCreate()


        instance = this


        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath(getString(R.string.app_font_regular))
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )

    }


    companion object {

        @JvmStatic
        var instance: App? = null
            private set

    }
}