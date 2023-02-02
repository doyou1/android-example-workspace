package com.example.startuponbootupbrsampling

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.startuponbootupbrsampling.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.tvResult.text = "date: ${getCurrentDate()}"
        binding.tvReceiver.text = "receiver: ${getReceiverData()}"
    }

    private fun getCurrentDate(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        return sdf.format(cal.time)
    }

    private fun getReceiverData() : String? {
        val pref = getSharedPreferences("receiver", Context.MODE_PRIVATE)
        return pref.getString("receiver", null)
    }
}