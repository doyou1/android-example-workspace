package com.example.startforegroundsampling

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import java.text.SimpleDateFormat
import java.util.*

class NotificationService : Service(), SensorEventListener {

    private val shutdownReceiver = ShutdownReceiver()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        reRegisterStepCounter()
        registerBroadcastReceiver()
        showNotification()
        // after 3 seconds
        val nextTime = System.currentTimeMillis() + (3 * 1000)
        val am = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getService(applicationContext, 2, Intent(this, NotificationService::class.java), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        if(Build.VERSION.SDK_INT >= 23) {
            am.setAndAllowWhileIdle(AlarmManager.RTC, nextTime, pendingIntent)
        } else {
            am.set(AlarmManager.RTC, nextTime, pendingIntent)
        }
        return START_STICKY
    }

    private fun reRegisterStepCounter() {
        val sm = getSystemService(SENSOR_SERVICE) as SensorManager

        try {
            sm.unregisterListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val sensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        // enable batching with delay of max 5 min
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL, (5 * MICROSECONDS_IN_ONE_MINUTE).toInt())
    }

    private fun registerBroadcastReceiver() {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SHUTDOWN);
        registerReceiver(shutdownReceiver, filter);
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
        notificationBuilder.setContentTitle(getDate()).setContentText("time: ${getTime()} count: ${getCount(context)} serviceSteps: ${getServiceSteps(context)}")
        notificationBuilder.setPriority(Notification.PRIORITY_MIN)
            .setShowWhen(false)
            .setContentIntent(PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE))
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
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

    private fun getCount(context: Context) : Int {
        val pref = context.getSharedPreferences("count", Context.MODE_PRIVATE)
        val count = pref.getInt("count", 0)
        pref.edit().putInt("count", count+1).apply()
        return count
    }

    private fun getServiceSteps(context: Context) : Int {
        val pref = getSharedPreferences("pedometer", Context.MODE_PRIVATE)
        return pref.getInt("service_steps", 0)
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_CHANNEL_ID = "Notification"
        const val MICROSECONDS_IN_ONE_MINUTE: Long = 60000000

    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { e ->
            if(e.values[0] > Integer.MAX_VALUE || e.values[0].toInt() == 0) return
            val pref = getSharedPreferences("pedometer", Context.MODE_PRIVATE)
            pref.edit().putInt("service_steps", e.values[0].toInt()).apply()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // won't happen
    }
}