package com.example.alarmmanagersampling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.alarmmanagersampling.databinding.ActivityDestinationBinding

class DestinationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDestinationBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        intent.getStringExtra(TEXT_DATE)?.let {
            binding.tvResult.text = it
        }
    }

    override fun onBackPressed() {
        if (intent.getStringExtra(TEXT_DATE) != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }
}