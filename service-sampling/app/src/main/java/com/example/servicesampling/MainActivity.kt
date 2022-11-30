package com.example.servicesampling

import android.app.ActivityManager
import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.servicesampling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG: String = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        resetCounterService()
        resetSharedPreferencesChangeListener()
    }

    private fun resetCounterService() {
        val intent = Intent(this, CounterService::class.java)
        stopService(intent)
        startService(intent)
    }

    private fun resetSharedPreferencesChangeListener() {
        val prefs = getSharedPreferences("seconds", Context.MODE_PRIVATE)
        prefs.unregisterOnSharedPreferenceChangeListener(null)
        prefs.registerOnSharedPreferenceChangeListener { prefs, key ->
            key?.let {
                if (it == "value") {
                    binding.tvSeconds.text = prefs?.getInt("value", 0).toString()
                }
            }
        }
    }
}