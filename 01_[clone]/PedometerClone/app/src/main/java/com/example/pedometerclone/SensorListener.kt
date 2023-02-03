package com.example.pedometerclone

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Build
import android.os.IBinder
import android.util.Log
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
        pref.getInt(TEXT_GOAL, 10000)

        val todayOffset = db.getSteps(Util.getToday())
        // use saved value if we haven't anything better
        if(steps == 0) steps = db.getCurrentSteps()
        
    }

    companion object {
        var steps = 0
        var lastSaveSteps = 0
        var lastSaveTime = 0L
    }

}