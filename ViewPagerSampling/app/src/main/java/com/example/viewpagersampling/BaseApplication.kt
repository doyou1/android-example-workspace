package com.example.viewpagersampling

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import java.util.*

class BaseApplication : Application() {

    private val TAG = this::class.java.simpleName

    override fun onCreate() {
        super.onCreate()

        setToday()
    }

    private fun setToday() {
        val calendar = Calendar.getInstance()
        val YYYY = calendar.get(Calendar.YEAR)
        val MM = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
        val DD = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))

        val sharedPreferences = getSharedPreferences(TEXT_TODAY, MODE_PRIVATE)
        sharedPreferences.edit().putString(TEXT_TODAY, "$YYYY$MM$DD").apply()
    }
}