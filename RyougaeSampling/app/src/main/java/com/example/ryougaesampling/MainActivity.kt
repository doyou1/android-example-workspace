package com.example.ryougaesampling

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ryougaesampling.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentPrice()

        binding.btnStart.setOnClickListener {
            setCurrentPrice()
        }
    }

    private fun setCurrentPrice() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                val result = RougaeUtil().getRougae()
                CoroutineScope(Dispatchers.Main).launch {
                    binding.tvResult.text = result
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}

