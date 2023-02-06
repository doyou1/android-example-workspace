package com.example.startforegroundsampling

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import java.text.SimpleDateFormat
import java.util.*

class NotificationService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()

        // after 10 seconds
        val nextTime = System.currentTimeMillis() + (10 * 1000)
        val am = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getService(applicationContext, 2, Intent(this, NotificationService::class.java), 0)

        if(Build.VERSION.SDK_INT >= 23) {
            am.setAndAllowWhileIdle(AlarmManager.RTC, nextTime, pendingIntent)
        } else {
            am.set(AlarmManager.RTC, nextTime, pendingIntent)
        }

        return START_STICKY
    }

    private fun showNotification() {
        if(Build.VERSION.SDK_INT >= 26) {
            startForeground(NOTIFICATION_ID, getNotification(this));
        } else {
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(NOTIFICATION_ID, getNotification(this))
        }
    }

    private fun getNotification(context: Context) : Notification {
        val notificationBuilder = if(Build.VERSION.SDK_INT >= 26) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_NONE)
            channel.importance = NotificationManager.IMPORTANCE_MIN; // ignored by Android O ...
            channel.enableLights(false);
            channel.enableVibration(false);
            channel.setBypassDnd(false);
            channel.setSound(null, null);
            manager.createNotificationChannel(channel);
            val builder = Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
            builder
        } else {
            Notification.Builder(context)
        }
        notificationBuilder.setContentTitle(getDate()).setContentText(getTime())
        notificationBuilder.setPriority(Notification.PRIORITY_MIN)
            .setShowWhen(false)
            .setContentIntent(PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
        return notificationBuilder.build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun getDate() : String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        return sdf.format(cal.time)
    }

    private fun getTime() : String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("HH:mm:ss")
        return sdf.format(cal.time)
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_CHANNEL_ID = "Notification"
    }
}