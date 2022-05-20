package com.example.servicethreadexample

import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import java.lang.Exception


class ServiceThread(val handler: Handler, val time: Long): Thread() {

    var isRun = true

    fun stopForever() {
        synchronized(this) {
            isRun = false
        }
    }

    override fun run() {

        while (isRun) {
            val currentTime = System.currentTimeMillis()
            val message = handler.obtainMessage()

            if (time + 10000 < currentTime) {
                isRun = false
                message.what = -1
                Log.e("ServiceThread", "complete")
            } else {
                message.what = 0
                message.obj = System.currentTimeMillis()
                Log.e("ServiceThread", "time: $time, currentTime: $currentTime")
            }

            handler.sendMessage(message)
            try {
                sleep(1000)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}