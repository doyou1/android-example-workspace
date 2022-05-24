package com.example.notificationchannelsampling

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class Alarm30Receiver: BroadcastReceiver() {
    private val TAG = this::class.java.simpleName.toString()
    override fun onReceive(context: Context?, intent: Intent?) {

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val yourmilliseconds = System.currentTimeMillis()
        val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm:ss")
        val resultdate = Date(yourmilliseconds)

        val builder = NotificationCompat.Builder(context!!, NotificationUtils.ANDROID_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Alarm30 Content Title")
            .setContentText("currentTime: ${sdf.format(resultdate)}")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManagerCompat = NotificationManagerCompat.from(context!!)
        notificationManagerCompat.notify(0, builder.build())

        Log.d(TAG, "Alarm30Receiver ${sdf.format(resultdate)}")

    }
}