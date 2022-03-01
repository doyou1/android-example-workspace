package com.example.seekbarproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import com.example.seekbarproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initialize()
        setSeekBarBrightness()
        setSeekBarSize()
    }

    private fun initialize() {
        // 최초 결과 텍스트 안보이는 문제로 seek_bar_brightness progress 초기값을 3으로 설정
        binding.tvResult.alpha =(binding.seekBarBrightness.progress * 0.2).toFloat()
    }

    private fun setSeekBarBrightness() {
        binding.seekBarBrightness.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.e(TAG, "onProgressChanged: $progress")
                binding.tvBrightness.text = "밝기(${progress} * 0.2)" // seekbar의 textview progress 변화 출력
                binding.tvResult.alpha = (progress * 0.2).toFloat() // text의 alpha는 float형(0.0 ~ 1.0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.e(TAG, "onStartTrackingTouch: $seekBar")

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.e(TAG, "onStopTrackingTouch: $seekBar")
            }
        })
    }

    private fun setSeekBarSize() {
        binding.seekBarSize.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.e(TAG, "onProgressChanged: $progress")

                binding.tvSize.text = "사이즈($progress + 20)"             // seekbar의 textview progress 변화 출력
                binding.tvResult.textSize = (20 + progress).toFloat()   // textSize는 float형
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.e(TAG, "onStartTrackingTouch: $seekBar")

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.e(TAG, "onStopTrackingTouch: $seekBar")
            }
        })
    }
}