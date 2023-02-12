package com.example.commutesampling

import android.app.Application
import android.content.Context

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val isInit = getSharedPreferences(TEXT_INIT, Context.MODE_PRIVATE).getBoolean(TEXT_INIT, false)
        if(!isInit) {
            val pref = getSharedPreferences(TEXT_SETTING, Context.MODE_PRIVATE)
            pref.edit().putString(TEXT_WORK_START_TIME, "09:00").apply()
            pref.edit().putString(TEXT_WORK_END_TIME, "18:00").apply()
            pref.edit().putString(TEXT_LUNCH_START_TIME, "12:00").apply()
            pref.edit().putString(TEXT_LUNCH_END_TIME, "13:00").apply()

            getSharedPreferences(TEXT_INIT, Context.MODE_PRIVATE).edit().putBoolean(TEXT_INIT, true).apply()
        }

    }
}