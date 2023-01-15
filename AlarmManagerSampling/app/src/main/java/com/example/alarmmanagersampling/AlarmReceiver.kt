package com.example.alarmmanagersampling

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    private val TAG = this::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {

        val intent = Intent(context, DestinationActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd, HH:mm:ss")
        val dateText = sdf.format(cal.time)
        intent.putExtra(TEXT_DATE, dateText)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val builder = NotificationCompat.Builder(context!!, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(NOTIFICATION_CHANNEL_CONTENT_TITLE)
            .setContentText("$NOTIFICATION_CHANNEL_CONTENT_TEXT $dateText")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManagerCompat = NotificationManagerCompat.from(context!!)
        notificationManagerCompat.notify(777, builder.build())
    }

}