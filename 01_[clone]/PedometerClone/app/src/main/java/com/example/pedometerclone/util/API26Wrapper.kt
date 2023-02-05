package com.example.pedometerclone.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
class API26Wrapper {
    companion object {

        private val NOTIFICATION_CHANNEL_ID = "Notification"

        fun startForegroundService(context: Context, intent: Intent) {
            context.startForegroundService(intent)
        }

        fun getNotificationBuilder(context: Context): Notification.Builder {
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_ID,
                NotificationManager.IMPORTANCE_NONE
            )
            // ignored by Android O...
            channel.importance = NotificationManager.IMPORTANCE_MIN
            channel.enableLights(false)
            channel.enableVibration(false)
            channel.enableVibration(false)
            channel.setBypassDnd(false)
            channel.setSound(null, null)
            manager.createNotificationChannel(channel)
            return Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
        }
    }
}