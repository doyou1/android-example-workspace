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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName

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
            setFragment(HomeFragment.getInstance())
        }

        binding.bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    setFragment(HomeFragment.getInstance())
                }
                R.id.history -> {
                    setFragment(HistoryFragment.getInstance())
                }
                R.id.setting -> {
                    setFragment(SettingFragment.getInstance())
                }
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayout.id, fragment)
            commit()
        }
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
                setFragment(HomeFragment.getInstance())
            }
        }
    }
}