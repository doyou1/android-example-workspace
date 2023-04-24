package com.example.gamesamplingv2.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.gamesamplingv2.R
import com.example.gamesamplingv2.databinding.ActivityAutoBackgroundBinding

class AutoBackgroundActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAutoBackgroundBinding
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAutoBackgroundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler.postDelayed({
            val maxSize =
                if (binding.root.width > binding.root.height) binding.root.width else binding.root.height
            binding.autoBackgroundView.start(R.drawable.bg_space, maxSize)
            binding.showUI = true
        }, DELAY_SHOW_UI)
    }
    override fun onPause() {
        super.onPause()
        binding.autoBackgroundView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.autoBackgroundView.destroy()
    }

    companion object {
        const val DELAY_SHOW_UI = 300L
    }
}