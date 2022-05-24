package com.example.notificationchannelsampling

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
class NotificationUtils(context: Context): ContextWrapper(context) {

    lateinit var manager:NotificationManager

    init {

        createChannels()
    }

    private fun createChannels() {

        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // create android channel
        val androidChannel = NotificationChannel(ANDROID_CHANNEL_ID, ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        // Sets whether notifications posted to this channel should display notification lights
        // 이 채널에 게시된 알림이 알림 표시등(notification light)을 표시해야 하는지 여부를 설정합니다.
        androidChannel.enableLights(true)

        // Sets whether notification posted to this channel should vibrate.
        // 이 채널에 게시된 알림이 진동해야 하는지 여부를 설정합니다.
        androidChannel.enableVibration(true)

        // Sets the notification light color for notifications posted to this channel
        // 이 채널에 게시된 알림에 대한 알림 표시등 색을 설정합니다.
        androidChannel.lightColor = Color.GREEN

        // Sets whether notifications posted to this channel appear on the lockscreen or not
        // 이 채널에 게시된 알림이 잠금 화면에 표시되는지 여부를 설정합니다.
        androidChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

        manager.createNotificationChannel(androidChannel)

        // create ios channel
        val iosChannel = NotificationChannel(IOS_CHANNEL_ID, IOS_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        iosChannel.enableLights(true)
        iosChannel.enableVibration(true)
        iosChannel.lightColor = Color.GREEN
        iosChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

        manager.createNotificationChannel(iosChannel)
    }

    fun getAndroidChannelNotification(title: String, body: String): Notification.Builder {
        return Notification.Builder(applicationContext, ANDROID_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(androidx.core.R.drawable.notification_bg)
            .setAutoCancel(true)
    }

    fun getIosChannelNotification(title: String, body: String): Notification.Builder {
        return Notification.Builder(applicationContext, IOS_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(androidx.core.R.drawable.notification_bg)
            .setAutoCancel(true)
    }

    companion object {
        val ANDROID_CHANNEL_ID = "ANDROID"
        val IOS_CHANNEL_ID = "IOS"
        val ANDROID_CHANNEL_NAME = "ANDROID CHANNEL"
        val IOS_CHANNEL_NAME = "IOS CHANNEL"
    }
}