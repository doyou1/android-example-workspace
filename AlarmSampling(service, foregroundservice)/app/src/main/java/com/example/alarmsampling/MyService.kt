package com.example.alarmsampling

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat

class MyService : Service() {

    override fun onCreate() {
        Log.e(TAG, "onCreate")
        super.onCreate()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand")

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e(TAG, "onBind")

        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy")

    }

    companion object {
        private val TAG = MyService::class.java.simpleName
    }


}