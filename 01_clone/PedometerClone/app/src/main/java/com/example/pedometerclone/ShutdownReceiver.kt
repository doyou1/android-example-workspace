package com.example.pedometerclone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.room.Room
import com.example.pedometerclone.room.DataBase
import com.example.pedometerclone.room.Steps
import com.example.pedometerclone.util.SensorListener
import com.example.pedometerclone.util.Util

class ShutdownReceiver : BroadcastReceiver() {

    private val TAG = this::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "shutting down")
        context?.let { c ->
            c.startService(Intent(context, SensorListener::class.java))

            // if the user used a root script for shutdown,
            // the DEVICE_SHUTDOWN broadcast might not be send.
            // Therefore, the app will check this setting on the next boot and displays an error message
            // if it's not set to true
            c.getSharedPreferences("pedometer", Context.MODE_PRIVATE)?.edit()?.putBoolean("correctShutdown", true)?.commit()
            val db = Room.databaseBuilder(c, DataBase::class.java, "steps").build().stepsDao()
            if(db.getSteps(Util.getToday()) == 0) {
                val steps = db.getCurrentSteps()
                db.insertNewDay(Steps(0, Util.getToday(), steps))
            } else {
                db.addToLastEntry(db.getCurrentSteps())
            }
        }
    }
}