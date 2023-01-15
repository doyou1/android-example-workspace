package com.example.animationsampling

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.animation.doOnEnd
import com.example.animationsampling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val DURATION = 3000L



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
        binding.btnRotate.setOnClickListener {
            doRotate()
        }
        binding.btnAlpha.setOnClickListener {
            doAlpha()
        }
        binding.btnTranslation.setOnClickListener {
            doTranslation()
        }
    }

    private fun doRotate() {
        ObjectAnimator.ofFloat(binding.btnRotate, "rotation", 0f, 360f).apply {
            duration = DURATION
            start()
        }
    }

    private fun doAlpha() {
        ObjectAnimator.ofFloat(binding.btnAlpha, "alpha", 1f, 0.5f, 0f, 0.5f, 1f).apply {
            duration = DURATION
            start()
        }
    }

    private fun doTranslation() {
        ObjectAnimator.ofFloat(binding.btnTranslation, "translationX", 0f, 100f, 0f).apply {
            duration = DURATION
            start()
        }.doOnEnd {
            ObjectAnimator.ofFloat(binding.btnTranslation, "translationY", 0f, 100f, 0f).apply {
                duration = DURATION
                start()
            }
        }
    }

}