package com.example.servicesampling

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class BaseApplication: Application() {

    private lateinit var prefs: SharedPreferences

    var seconds: Int = 0

    override fun onCreate() {
        super.onCreate()
        prefs = getSharedPreferences("seconds", Context.MODE_PRIVATE)
        seconds = prefs.getInt("value", 0);
    }

    fun set(value: Int) {
        prefs.edit().clear().putInt("value", value).apply()
        seconds = value
    }
}