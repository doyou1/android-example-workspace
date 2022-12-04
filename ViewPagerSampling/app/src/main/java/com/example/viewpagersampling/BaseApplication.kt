package com.example.viewpagersampling

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import java.util.*

class BaseApplication : Application() {

    private val TAG = this::class.java.simpleName

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences(TEXT_DAY_OF_MONTH, MODE_PRIVATE)
        setDayOfMonth()
    }

    private fun setDayOfMonth() {
        val calendar = Calendar.getInstance()
        val DD = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
        sharedPreferences.edit().putString(TEXT_DAY_OF_MONTH, DD).apply()
    }

    companion object {
        lateinit var sharedPreferences: SharedPreferences

    }
}