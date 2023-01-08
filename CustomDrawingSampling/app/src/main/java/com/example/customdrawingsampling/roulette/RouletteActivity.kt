package com.example.customdrawingsampling.roulette

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customdrawingsampling.MAX_ROULETTE_SIZE
import com.example.customdrawingsampling.MIN_ROULETTE_SIZE
import com.example.customdrawingsampling.databinding.ActivityRouletteBinding

class RouletteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRouletteBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouletteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnMinus.setOnClickListener {
            val prev = binding.tvCount.text.toString().toInt()
            val result = if (prev - 1 < MIN_ROULETTE_SIZE) {
                MIN_ROULETTE_SIZE
            } else {
                prev - 1
            }
            binding.tvCount.text = result.toString()

            if (prev != result) binding.rouletteView.setSize(result)
        }

        binding.btnPlus.setOnClickListener {
            val prev = binding.tvCount.text.toString().toInt()
            val result = if (prev + 1 > MAX_ROULETTE_SIZE) {
                MAX_ROULETTE_SIZE
            } else {
                prev + 1
            }
            binding.tvCount.text = result.toString()
            if (prev != result) binding.rouletteView.setSize(result)
        }
    }
}