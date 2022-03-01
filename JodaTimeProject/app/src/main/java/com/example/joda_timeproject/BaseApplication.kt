package com.example.joda_timeproject

import android.app.Application
import android.util.Log
import net.danlew.android.joda.JodaTimeAndroid

class BaseApplication : Application() {

    private val TAG = "BaseApplication"

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate")

        JodaTimeAndroid.init(this)
    }
}