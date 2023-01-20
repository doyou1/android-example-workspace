package com.example.dpandspproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import com.example.dpandspproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private var textSize = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        textSize = 1
        updateTextSize()

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
    }

    private fun setClickEvent() {

        binding.btnMinus.setOnClickListener {
            if (textSize - 1 < 1) {
                textSize = 1
            } else {
                textSize--
            }
            updateTextSize()
        }

        binding.btnPlus.setOnClickListener {
            textSize++
            updateTextSize()
        }
    }

    private fun updateTextSize() {
        binding.tvDp.text = "${textSize}dp"
        binding.tvSp.text = "${textSize}sp"
        binding.tvIn.text = "${textSize}in"
        binding.tvMm.text = "${textSize}mm"
        binding.tvPt.text = "${textSize}pt"
        binding.tvPx.text = "${textSize}px"
        binding.tvDp.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
        binding.tvSp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize.toFloat())
        binding.tvIn.setTextSize(TypedValue.COMPLEX_UNIT_IN, textSize.toFloat())
        binding.tvMm.setTextSize(TypedValue.COMPLEX_UNIT_MM, textSize.toFloat())
        binding.tvPt.setTextSize(TypedValue.COMPLEX_UNIT_PT, textSize.toFloat())
        binding.tvPx.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
    }

}