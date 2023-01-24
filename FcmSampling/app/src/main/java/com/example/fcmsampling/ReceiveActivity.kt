package com.example.fcmsampling

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.example.fcmsampling.databinding.ActivityReceiveBinding

class ReceiveActivity : Activity() {

    private lateinit var binding: ActivityReceiveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setPreferences()
    }

    private fun setPreferences() {
        val pref = getSharedPreferences("DATA", Context.MODE_PRIVATE)
        binding.tvDate.text = pref.getString("DATE", null)
        binding.tvTitle.text = pref.getString("TITLE", null)
        binding.tvContent.text = pref.getString("CONTENT", null)
    }
}