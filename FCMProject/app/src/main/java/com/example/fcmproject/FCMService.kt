package com.example.fcmproject

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    private val TAG = this::class.java.simpleName

    private val REQUEST_CODE = 100
    private val notificationId = 777

    // channel id, channel name
    private val channelId = "notification_channel"
    private val channelName = "notification_name"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.e(TAG, "onMessageReceived")
        remoteMessage.notification?.let { notification ->
            generateNotification(notification.title!!, notification.body!!)
        }
    }


    // generate the notification
    private fun generateNotification(title: String, message: String) {
        val intent = Intent(this, ReceiveActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("title", title)
        intent.putExtra("message", message)

        val pendingIntent =
            PendingIntent.getActivity(this, REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(R.drawable.jamesweb)
                // 要確認
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(notificationId, builder.build())
    }

    // attach the notification created with the custom layout
    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.example.fcmproject", R.layout.layout_notification)
        remoteView.setTextViewText(R.id.tv_title, title)
        remoteView.setTextViewText(R.id.tv_message, message)
        remoteView.setImageViewResource(R.id.logo_notification, R.drawable.jamesweb)
        return remoteView
    }


    // show the notification

}