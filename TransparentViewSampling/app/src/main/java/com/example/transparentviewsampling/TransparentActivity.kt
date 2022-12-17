package com.example.transparentviewsampling

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.transparentviewsampling.databinding.ActivityTransparentBinding

class TransparentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTransparentBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransparentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e(TAG, "onCreate")
    }
}