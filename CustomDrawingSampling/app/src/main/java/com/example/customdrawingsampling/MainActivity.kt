package com.example.customdrawingsampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customdrawingsampling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.btnClear.setOnClickListener {
            binding.touchDrawView.clear()
        }
    }
}