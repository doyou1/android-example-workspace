package com.example.pedometersampling

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pedometersampling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityMainBinding
    private var steps = 0;
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkActivityPermission()

        binding.tvSensor.text = steps.toString()
        // Create Sensor Manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        // Step Detect Sensor
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkActivityPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
        }
    }

    override fun onResume() {
        super.onResume()
        sensor.let {
            // 센서 속도 설정
            // 옵션
            // - SENSOR_DELAY_NORMAL : 20000초 딜레이
            // - SENSOR_DELAY_UI : 6000초 딜레이
            // - SENSOR_DELAY_GAME : 20000 초 딜레이
            // - SENSOR_DELAY_FASTEST : 딜레이 없음
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        println("onSensorChanged")

        // 센서 유형이 스텝감지 센서인 경우 걸음수 +1
        if(event?.sensor?.type == Sensor.TYPE_STEP_DETECTOR) {

            if(event?.values?.get(0) ?: 0 == 1.0f) {
                binding.tvSensor.text = (++steps).toString()
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}