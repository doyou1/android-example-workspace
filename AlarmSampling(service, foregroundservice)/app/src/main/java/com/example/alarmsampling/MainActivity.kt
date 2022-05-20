package com.example.alarmsampling

import android.content.*
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.alarmsampling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnStart.setOnClickListener {
            startBasicService()
        }
        binding.btnStop.setOnClickListener {
            stopBasicService()
        }

        binding.btnStartForeground.setOnClickListener {
            startForegroundService()
        }
        binding.btnStopForeground.setOnClickListener {
            stopForegroundService()
        }
    }

    private fun startBasicService() {
        Intent(this, MyService::class.java).run {
            startService(this)
        }
    }

    private fun stopBasicService() {
        Intent(this, MyService::class.java).run {
            stopService(this)
        }
    }

    private fun startForegroundService() {
        Intent(this, MyForegroundService::class.java).run {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) startForegroundService(this)
            else startService(this)
        }
    }

    private fun stopForegroundService() {
        Intent(this, MyForegroundService::class.java).run {
            stopService(this)
        }
    }
}