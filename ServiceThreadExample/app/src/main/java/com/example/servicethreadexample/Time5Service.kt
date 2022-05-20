package com.example.servicethreadexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

// 5분에 한번씩 시작된다.
class Time5Service: Service() {
    private var thread: Time5ServiceThread? = null
    private lateinit var mContext: Context

    private val notificationManager get() = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("Time5Service", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("Time5Service", "onStartCommand")

        mContext = this

        val handler = Time5ServiceHandler()
        thread = Time5ServiceThread(handler = handler)
        thread?.start()

        return START_STICKY
    }

    override fun onDestroy() {
        Log.e("Time5Service", "onDestroy")

        thread?.stopForever()
        thread = null
    }

    private fun createNotifcation(objStr: String) {

        // createNotificationChannel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.description = "AlarmManager Tests"
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // deliverNotification(applicationContext)
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(applicationContext, NOTIFICATION_ID,contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(applicationContext, PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Alert")
            .setContentText("objStr: $objStr")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    inner class Time5ServiceHandler: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.e("Time5ServiceHandler", "Message: ${msg}")
            if (msg.what == 0) {
//                thread = null
                createNotifcation(msg.obj.toString())
            }
        }
    }

    inner class Time5ServiceThread(val handler: Time5ServiceHandler): Thread() {
        var isRun = true

        var oldSecond = -1

        fun stopForever() {
            synchronized(this) {
                isRun = false
            }
        }

        override fun run() {
            while (isRun) {
                val yourMs = System.currentTimeMillis()
                val minuteFormat = SimpleDateFormat("mm")
                val minuteFormatted = Date(yourMs)
                val currentMinute = minuteFormat.format(minuteFormatted).toInt()  // 현재 분

                val message = handler.obtainMessage()
                if (currentMinute%5 == 0) {
                    val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm:ss")
                    val resultDate = Date(yourMs)
                    message.what = 0
                    message.obj = sdf.format(resultDate)
                    handler.sendMessage(message)
                } else {
                    message.what = -1
                    handler.sendMessage(message)
                }

                // Sleep 초 계산 기능
                val secondFormat = SimpleDateFormat("ss")
                val secondFormatted = Date(yourMs)
                val currentSecond = secondFormat.format(secondFormatted).toInt()  // 현재 초

                var sleepSecond = 60
                if (currentSecond in 0..5) {
                    sleepSecond = 60
                } else if (currentSecond in 6..59) {
                    sleepSecond = 60 - currentSecond
                }

                Log.e("Service", "currentMinute: $currentMinute, currentSecond: $currentSecond")
                try {
                    sleep((sleepSecond * 1000).toLong())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        const val TAG = "AlarmSampling"
        const val NOTIFICATION_ID = 0
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }
}