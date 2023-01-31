package com.example.servicesampling

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class BaseApplication: Application() {

    private lateinit var prefs: SharedPreferences
    private val TAG = this::class.java.simpleName
    var seconds: Int = 0

    override fun onCreate() {
        super.onCreate()
        prefs = getSharedPreferences("seconds", Context.MODE_PRIVATE)
        seconds = prefs.getInt("value", 0);
    }

    fun add() {
        Log.e(TAG, "add")
        seconds++
        prefs.edit().clear().putInt("value", seconds).apply()
        prefs.edit().clear().putString("date", getCurrentDate()).apply()
    }

    fun reset() {
        Log.e(TAG, "reset")
        seconds = 0
        prefs.edit().clear().putInt("value", 0).apply()
        prefs.edit().clear().putString("date", null).apply()
    }

    private fun getCurrentDate() : String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        return sdf.format(cal.time)
    }
}