package com.example.alarmsampling

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.*

class MyForegroundService : Service() {

    private val notificationManager get() = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    override fun onCreate() {
        registerDefaultNotificationChannel()
    }

    private fun registerDefaultNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(createDefaultNotificationChannel())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createDefaultNotificationChannel() = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
        description = CHANNEL_DESCRIPTION
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification())

        return START_STICKY
    }

    private fun createNotification() =
        NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle("Notification Title")
            val yourmilliseconds = System.currentTimeMillis()
            val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm:ss")
            val resultdate = Date(yourmilliseconds)
            setContentText("currentTime: ${sdf.format(resultdate)}")
            setSmallIcon(R.drawable.ic_launcher_background)

            setContentIntent(
                PendingIntent.getActivity(
                    this@MyForegroundService, 0, Intent(this@MyForegroundService, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }, 0
                )
            )
        }.build()

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private const val NOTIFICATION_ID = 1
//        private const val NOTIFICATION_COMPLETE_ID = 2
        private const val CHANNEL_ID = "my_channel"
        private const val CHANNEL_NAME = "default"
        private const val CHANNEL_DESCRIPTION = "This is default notification channel"
    }
}