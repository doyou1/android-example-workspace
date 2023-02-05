package com.example.pedometerclone.util

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
import android.util.Log
import androidx.room.Room
import com.example.pedometerclone.MainActivity
import com.example.pedometerclone.R
import com.example.pedometerclone.ShutdownReceiver
import com.example.pedometerclone.room.DataBase
import com.example.pedometerclone.room.Steps
import com.example.pedometerclone.widget.WidgetUpdateService
import kotlinx.coroutines.*
import java.text.NumberFormat
import java.util.*
import kotlin.math.min


class SensorListener : Service(), SensorEventListener {

    private val TAG = this::class.java.simpleName
    private val NOTIFICATION_ID = 1

    private val SAVE_OFFSET_TIME = AlarmManager.INTERVAL_HOUR
    private val SAVE_OFFSET_STEPS = 500

    private val shutdownReceiver = ShutdownReceiver()

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d(TAG, "${sensor?.name} accuracy changed: $accuracy")
    }

    override fun onSensorChanged(e: SensorEvent?) {
        e?.let { event ->
            if (event.values[0] > Integer.MAX_VALUE) {
                Log.d(TAG, "probably not a real value: ${event.values[0]}")
            } else {
                steps = event.values[0].toInt()
                CoroutineScope(Dispatchers.IO).launch {
                    updateIfNecessary()
                }
            }
        }
    }

    private suspend fun updateIfNecessary(): Boolean {
        if (steps > lastSaveSteps + SAVE_OFFSET_STEPS || (steps > 0 && System.currentTimeMillis() > lastSaveTime + SAVE_OFFSET_TIME)) {
            Log.d(
                TAG, "saving steps: steps= $steps, lastSave= $lastSaveSteps \n lastSaveTime= ${
                    Date(
                        lastSaveTime
                    )
                }"
            )

            val db = Room.databaseBuilder(this, DataBase::class.java, "steps").build().stepsDao()
            // is saved on today
            if (db.getSteps(Util.getToday()) == 0) {
                val pauseDifference =
                    steps - getSharedPreferences(TEXT_PEDOMETER, Context.MODE_PRIVATE).getInt(
                        TEXT_PAUSECOUNT, steps
                    )
                db.insertNewDay(Steps(0, Util.getToday(), steps - pauseDifference))
                if (pauseDifference > 0) {
                    // update pauseCount for the new day
                    getSharedPreferences(TEXT_PEDOMETER, Context.MODE_PRIVATE).edit().putInt(
                        TEXT_PAUSECOUNT, steps
                    ).commit()
                }
            }

            db.saveCurrentSteps(steps)
            lastSaveSteps = steps
            lastSaveTime = System.currentTimeMillis()

            // update notification
            showNotification()

            WidgetUpdateService.enqueueUpdate(this)
            return true
        } else {
            return false
        }
    }

    private suspend fun showNotification() {
        if (Build.VERSION.SDK_INT >= 26) {
            startForeground(NOTIFICATION_ID, getNotification(this))
        } else if (getSharedPreferences(TEXT_PEDOMETER, MODE_PRIVATE)
                .getBoolean(TEXT_NOTIFICATION, true)
        ) {
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .notify(NOTIFICATION_ID, getNotification(this))
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        reRegisterSensor()
        registerBroadcastReceiver()
        val context = this
        CoroutineScope(Dispatchers.IO).launch {
            if (!updateIfNecessary()) {
                showNotification()
            }

            // restart service every hour to save the current step count
            val nextUpdate =
                min(Util.getTomorrow(), System.currentTimeMillis() + AlarmManager.INTERVAL_HOUR)
            Log.d(TAG, "next update: ${Date(nextUpdate).toLocaleString()}")
            val am = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getService(
                applicationContext,
                2,
                Intent(context, SensorListener::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            if (Build.VERSION.SDK_INT >= 23) {
                API23Wrapper.setAlarmWhileIdle(am, AlarmManager.RTC, nextUpdate, pendingIntent)
            } else {
                am.set(AlarmManager.RTC, nextUpdate, pendingIntent)
            }
        }
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "SensorListener onCreate")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d(TAG, "sensor service task removed")
        // Restart service in 500 ms
        (getSystemService(Context.ALARM_SERVICE) as AlarmManager)
            .set(
                AlarmManager.RTC,
                System.currentTimeMillis() + 500,
                PendingIntent.getActivity(this, 3, Intent(this, SensorListener::class.java), 0)
            )
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "SensorListener onDestroy")
        try {
            val sm = (getSystemService(SENSOR_SERVICE) as SensorManager)
            sm.unregisterListener(this)
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
            e.printStackTrace()
        }
    }

    private suspend fun getNotification(context: Context): Notification =
        withContext(Dispatchers.IO) {
            Log.d(TAG, "getNotification")

            val db = Room.databaseBuilder(context, DataBase::class.java, "steps").build().stepsDao()
            val pref = context.getSharedPreferences(TEXT_PEDOMETER, Context.MODE_PRIVATE)
            val goal = pref.getInt(TEXT_GOAL, 10000)

            var todayOffset = db.getSteps(Util.getToday())
            // use saved value if we haven't anything better
            if (steps == 0) steps = db.getCurrentSteps()

            val notificationBuilder =
                if (Build.VERSION.SDK_INT >= 26) API26Wrapper.getNotificationBuilder(
                    context
                )
                else Notification.Builder(context)

            if (steps > 0) {
                if (todayOffset == Integer.MIN_VALUE) todayOffset = -steps
                val format = NumberFormat.getInstance(Locale.getDefault())
                val contentText =
                    if (todayOffset + steps >= goal) "Goal reached! ${format.format(todayOffset + steps)} steps and counting"
                    else "${goal - todayOffset - steps} steps to go"
                val contentTitle = "${format.format(todayOffset + steps)} steps"
                notificationBuilder.setProgress(goal, todayOffset + steps, false)
                    .setContentText(contentText).setContentTitle(contentTitle)
            }
            // still no steps value
            else {
                val contentText = "Your progress will be shown here soon"
                val contentTitle = "Pedometer is counting"
                notificationBuilder.setContentText(contentText).setContentTitle(contentTitle)
            }

            notificationBuilder.setPriority(Notification.PRIORITY_HIGH).setShowWhen(false)
                .setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent(context, MainActivity::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
                .setSmallIcon(R.drawable.ic_launcher_foreground).setOngoing(true)
            return@withContext notificationBuilder.build()
        }

    private fun registerBroadcastReceiver() {
        Log.d(TAG, "register broadcastreceiver")
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SHUTDOWN)
        registerReceiver(shutdownReceiver, filter)
    }

    private fun reRegisterSensor() {
        Log.d(TAG, "re-register sensor listener")

        val sm = getSystemService(SENSOR_SERVICE) as SensorManager

        try {
            sm.unregisterListener(this)
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
            e.printStackTrace()
        }

        Log.d(TAG, "step sensors: ${sm.getSensorList(Sensor.TYPE_STEP_COUNTER).size}")
        if (sm.getSensorList(Sensor.TYPE_STEP_COUNTER).size < 1) return  // emulator
        Log.d(TAG, "default: ${sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER).name}")

        sm.registerListener(
            this,
            sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
            SensorManager.SENSOR_DELAY_NORMAL,
            (5 * MICROSECONDS_IN_ONE_MINUTE).toInt()
        )
    }
    companion object {
        var steps = 0
        var lastSaveSteps = 0
        var lastSaveTime = 0L
        private const val MICROSECONDS_IN_ONE_MINUTE: Long = 60000000
    }
}