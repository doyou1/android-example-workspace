package com.example.pedometerclone

import android.app.*
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Build
import android.os.IBinder
import android.util.Log
import java.text.NumberFormat
import java.util.*


class SensorListener : Service(), SensorEventListener {

    private val TAG = this::class.java.simpleName
    private val NOTIFICATION_ID = 1

    private val SAVE_OFFSET_TIME = AlarmManager.INTERVAL_HOUR
    private val SAVE_OFFSET_STEPS = 500

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d(TAG, "${sensor?.name} accuracy changed: $accuracy")
    }

    override fun onSensorChanged(e: SensorEvent?) {
        e?.let { event ->
            if(event.values[0] > Integer.MAX_VALUE) {
                Log.d(TAG, "probably not a real value: ${event.values[0]}")
            } else {
                steps = event.values[0].toInt()
                updateIfNecessary()
            }
        }
    }

    private fun updateIfNecessary() : Boolean {
        if (steps > lastSaveSteps + SAVE_OFFSET_STEPS || (steps > 0 && System.currentTimeMillis() > lastSaveTime + SAVE_OFFSET_TIME)) {
            Log.d(TAG, "saving steps: steps= $steps, lastSave= $lastSaveSteps \n lastSaveTime= ${Date(lastSaveTime)}")

            // is saved on today
            if(db.getSteps(Util.getToday()) == Integer.MIN_VALUE) {
                val pauseDifference = steps - getSharedPreferences(TEXT_PEDOMETER, Context.MODE_PRIVATE).getInt(
                    TEXT_PAUSECOUNT, steps)
                db.insertNewDay(Util.getToday(), steps - pauseDifference)
                if(pauseDifference > 0) {
                    // update pauseCount for the new day
                    getSharedPreferences(TEXT_PEDOMETER, Context.MODE_PRIVATE).edit().putInt(
                        TEXT_PAUSECOUNT, steps).commit()
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

    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= 26) {
            startForeground(NOTIFICATION_ID, getNotification(this))
        } else if (getSharedPreferences(TEXT_PEDOMETER, MODE_PRIVATE)
                .getBoolean(TEXT_NOTIFICATION, true)
        ) {
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .notify(NOTIFICATION_ID, getNotification(this))
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun getNotification(context: Context) : Notification {
        Log.d(TAG, "getNotification")
        val pref = context.getSharedPreferences(TEXT_PEDOMETER, Context.MODE_PRIVATE)
        val goal = pref.getInt(TEXT_GOAL, 10000)

        var todayOffset = db.getSteps(Util.getToday())
        // use saved value if we haven't anything better
        if(steps == 0) steps = db.getCurrentSteps()

        val notificationBuilder = if(Build.VERSION.SDK_INT >= 26) API26Wrapper.getNotificationBuilder(context)
        else Notification.Builder(context)

        if(steps > 0) {
            if(todayOffset == Integer.MIN_VALUE) todayOffset = - steps
            val format = NumberFormat.getInstance(Locale.getDefault())
            val contentText = if(todayOffset + steps >= goal) "Goal reached! ${format.format(todayOffset+steps)} steps and counting"
            else "${goal - todayOffset - steps} steps to go"
            val contentTitle = "${format.format(todayOffset + steps)} steps"
            notificationBuilder.setProgress(goal, todayOffset + steps, false).setContentText(contentText).setContentTitle(contentTitle)
        }
        // still no steps value
        else {
            val contentText = "Your progress will be shown here soon"
            val contentTitle = "Pedometer is counting"
            notificationBuilder.setContentText(contentText).setContentTitle(contentTitle)
        }

        notificationBuilder.setPriority(Notification.PRIORITY_HIGH).setShowWhen(false)
            .setContentIntent(PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT))
            .setSmallIcon(R.drawable.ic_launcher_foreground).setOngoing(true)
        return notificationBuilder.build()
    }

    companion object {
        var steps = 0
        var lastSaveSteps = 0
        var lastSaveTime = 0L
    }

}