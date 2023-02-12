package com.example.chartsampling

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chartsampling.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter

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
        binding.btnDefaultChart.setOnClickListener {
            startActivity(Intent(this, DefaultChartActivity::class.java))
        }

        binding.btnCircleChart.setOnClickListener {
            startActivity(Intent(this, CircleChartActivity::class.java))
        }
    }

}