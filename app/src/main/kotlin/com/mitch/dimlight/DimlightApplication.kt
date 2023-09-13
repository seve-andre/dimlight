package com.mitch.dimlight

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DimlightApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // BuildConfig will be created after first run of the app
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
