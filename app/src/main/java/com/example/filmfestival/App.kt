package com.example.filmfestival

import android.app.Application
import com.posthog.android.PostHogAndroid
import com.posthog.android.PostHogAndroidConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application(){
    companion object {
        private const val POSTHOG_API_KEY = "phc_V44p0UChjXIfHY84uaFYK1T9BeglsMmO8ZARD05VS6C"
        private const val POSTHOG_HOST = "https://eu.i.posthog.com"
    }

    override fun onCreate() {
        super.onCreate()
        val config = PostHogAndroidConfig(POSTHOG_API_KEY, POSTHOG_HOST)
        PostHogAndroid.setup(this, config)
    }

}