package com.example.pedometersampling

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.pedometersampling.databinding.ActivityMainBinding
import com.example.pedometersampling.fragment.BaseFragment
import com.example.pedometersampling.fragment.HistoryFragment
import com.example.pedometersampling.fragment.HomeFragment
import com.example.pedometersampling.fragment.SettingFragment
import com.example.pedometersampling.room.DBHelper
import com.example.pedometersampling.room.Pedometer
import com.example.pedometersampling.service.PedometerService
import com.example.pedometersampling.util.REQUEST_CODE_STEP_COUNT
import com.example.pedometersampling.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
    private var item: Pedometer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= 26) {
            startForegroundService(Intent(this, PedometerService::class.java))
        } else {
            startService(Intent(this, PedometerService::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        if (checkActivityPermission(this)) {
            updateSteps()
            setStepCounter()
        }

        // init
        setFragment(HomeFragment.getInstance())

        binding.bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    setFragment(HomeFragment.getInstance())
                    updateSteps()
                }
                R.id.history -> {
                    setFragment(HistoryFragment.getInstance())
                    updateSteps()
                }
                R.id.setting -> {
                    setFragment(SettingFragment.getInstance())
                    updateSteps()
                }
            }
            true
        }
    }

    private fun updateSteps() {
        val context = this
        lifecycleScope.launch(Dispatchers.IO) {
            item = DBHelper.getCurrent(context = context)
            Log.d(TAG, "pedometer: $item")
            lifecycleScope.launch(Dispatchers.Main) {
                (supportFragmentManager.findFragmentById(binding.frameLayout.id) as BaseFragment).updateSteps(
                    item
                )
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayout.id, fragment)
            commit()
        }
    }

    private fun setStepCounter() {
        val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI, 0)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { e ->
            if (e.values[0] > Integer.MAX_VALUE || e.values[0].toInt() == 0) return
            DBHelper.process(this, e.values[0].toInt())
            updateSteps()
        }

        Log.d(TAG, "onSensorChanged")
    }

    private fun checkActivityPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    REQUEST_CODE_STEP_COUNT
                )
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
        if (requestCode == REQUEST_CODE_STEP_COUNT) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                updateSteps()
                setStepCounter()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopStepCounter()
    }

    private fun stopStepCounter() {
        val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sm.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // won't happen
    }
}