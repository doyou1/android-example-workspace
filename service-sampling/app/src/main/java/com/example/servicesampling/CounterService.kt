package com.example.servicesampling

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

class CounterService : Service() {

    private val TAG:String = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())
    private var isRun = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler.removeCallbacksAndMessages(null)
        isRun = true
        setSavedSeconds()
        setCounter()
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun setSavedSeconds() {
        val prefs = getSharedPreferences("seconds", Context.MODE_PRIVATE)
        val value = prefs.getInt("value", 0)
        seconds = value
    }

    private fun setCounter() {
        if(isRun) (application as BaseApplication).set(++seconds)
        handler.postDelayed(::setCounter, 1000)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        isRun = false
    }

    companion object {
        var seconds = 0
    }
}