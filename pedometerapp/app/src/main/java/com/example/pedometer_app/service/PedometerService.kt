package com.example.pedometer_app.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import com.example.pedometer_app.BaseApplication

class PedometerService : Service(), SensorEventListener {

    private val TAG:String = this::class.java.simpleName
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private var isRun = false



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create Sensor Manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        // Step Detect Sensor
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        isRun = true

        setSavedStep()
        setPedometer()

        return START_STICKY
    }

    private fun setSavedStep() {
        val prefs = getSharedPreferences("step", Context.MODE_PRIVATE)
        val value = prefs.getInt("value", 0)
        step = value
    }

    private fun setPedometer() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.sensor?.let {
            if(it.type == Sensor.TYPE_STEP_DETECTOR && event.values[0] == 1.0f) {
                Log.e(TAG, "sensorChanged $isRun $step")
                if(isRun) (application as BaseApplication).set(++step)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRun = false
    }

    companion object {
        var step = 0
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}