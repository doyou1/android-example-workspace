package com.example.startforegroundsampling

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.startforegroundsampling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName

    @RequiresApi(Build.VERSION_CODES.O)
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
        setPrefs()
    }

    private fun setPrefs() {
        val pref = getSharedPreferences("count", Context.MODE_PRIVATE)
        val count = pref.getInt("count", 0)
        binding.tvResult.text = "count: $count"
    }
}