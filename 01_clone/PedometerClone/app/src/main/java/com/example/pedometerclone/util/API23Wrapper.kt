package com.example.pedometerclone.util

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class API23Wrapper {
    companion object {

        private val NOTIFICATION_CHANNEL_ID = "Notification"

        fun setAlarmWhileIdle(am: AlarmManager, type: Int, time: Long, intent: PendingIntent) {
            am.setAndAllowWhileIdle(type, time, intent)
        }
    }
}