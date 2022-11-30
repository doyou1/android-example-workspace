package com.example.pedometer_app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class BaseApplication: Application() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        prefs = getSharedPreferences("step", Context.MODE_PRIVATE)
    }

    fun set(value: Int) {
        prefs.edit().clear().putInt("value", value).apply()
    }
}