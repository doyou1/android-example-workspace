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
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnClear.setOnClickListener {
            (application as BaseApplication).reset()
            binding.tvSeconds.text = "0"
            binding.tvDate.text = ""
        }

        binding.btnStart.setOnClickListener {
            resetCounterService()
        }

        binding.btnStop.setOnClickListener {
            val intent = Intent(this, CounterService::class.java)
            stopService(intent)
        }
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
                } else if (it == "date") {
                    binding.tvDate.text = prefs?.getString("date", null).toString()
                }
            }
        }
    }
}