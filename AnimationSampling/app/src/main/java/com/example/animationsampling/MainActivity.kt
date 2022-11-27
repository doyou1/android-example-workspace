package com.example.animationsampling

import android.R
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.translationMatrix
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import com.example.animationsampling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btn.setOnClickListener {
            doAnimation()
        }
    }

    private fun doAnimation() {
        ObjectAnimator.ofFloat(binding.btn, "alpha", 1f, 0.5f, 0f).apply {
            duration = 2000
            start()
        }
    }

}