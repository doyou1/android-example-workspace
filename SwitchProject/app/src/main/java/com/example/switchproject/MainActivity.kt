package com.example.switchproject

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.switchproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    // texts, colors, visibilities
    // Switch checked 여부에 따라
    private val texts = mapOf(false to "텍스트", true to "TEXT")
    private val colors = mapOf(false to Color.rgb(0,0,0), true to Color.rgb(255,0,0))
    private val textStyles = mapOf(false to Typeface.NORMAL, true to Typeface.BOLD)
    private val visibilities = mapOf(false to View.VISIBLE, true to View.INVISIBLE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSwitchToggleEvent()
    }

    private fun setSwitchToggleEvent() {
        binding.swiTextColor.setOnCheckedChangeListener { compoundButton, isChecked ->
            colors[isChecked]?.let {
                binding.tvResult.setTextColor(it)
            }
        }
        binding.swiTextBold.setOnCheckedChangeListener { compoundButton, isChecked ->
            textStyles[isChecked]?.let {
                binding.tvResult.setTypeface(null, it)
            }
            // null을 setTypeface()로 전달하는 것은
            // TextView가 이전에 설정한 Typeface와 다를 수 있는 하드 코딩된 기본값을 사용함을 의미합니다.
            // binding.tvResult.typeface은 기존 스타일과 함께 적용한다는 것을 의미해서 우선순위에 따라 Bold to Normal이 진행 안됨
//                binding.tvResult.setTypeface(binding.tvResult.typeface, Typeface.BOLD)

            // true or null, nullpointer exception 발생
//            Log.e(TAG, "binding.tvResult.typeface.isBold: ${binding.tvResult.typeface.isBold ?: "null"}")
        }
        binding.swiTextVisibility.setOnCheckedChangeListener { compoundButton, isChecked ->
            visibilities[isChecked]?.let {
                binding.tvResult.visibility = it
            }
        }
        binding.swiTextChange.setOnCheckedChangeListener { compoundButton, isChecked ->
            texts[isChecked]?.let {
                binding.tvResult.text = it
            }
        }
    }

}