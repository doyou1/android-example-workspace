package com.example.startforegroundsampling

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.startforegroundsampling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= 26) {
            startForegroundService(Intent(this, NotificationService::class.java))
        } else {
            startService(Intent(this, NotificationService::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        if(checkActivityPermission()) {
            setCount()
            updateSteps()
            setStepCounter()
        }
    }

    private fun checkActivityPermission() : Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0) {
            if(grantResults[0] == PackageManager.PERMISSION_DENIED) {
                setCount()
                updateSteps()
                setStepCounter()
            }
        }
    }

    private fun setCount() {
        val pref = getSharedPreferences("count", Context.MODE_PRIVATE)
        val count = pref.getInt("count", 0)
        binding.tvCount.text = "count: $count"
    }

    private fun updateSteps() {
        val pref = getSharedPreferences("pedometer", Context.MODE_PRIVATE)
        val steps = pref.getInt("steps", 0)
        binding.tvSteps.text = "steps: $steps"

        val serviceSteps = pref.getInt("service_steps", 0)
        binding.tvServiceSteps.text = "service: $serviceSteps"
    }

    private fun setStepCounter() {
        val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI, 0)
    }

    override fun onPause() {
        super.onPause()
        stopStepCounter()
    }

    private fun stopStepCounter() {
        val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sm.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { e ->
            if (e.values[0] > Integer.MAX_VALUE || e.values[0].toInt() == 0) return
            val pref = getSharedPreferences("pedometer", Context.MODE_PRIVATE)
            pref.edit().putInt("steps", e.values[0].toInt()).apply()
            updateSteps()
        }

        Log.d(TAG, "onSensorChanged")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // won't happen
    }
}