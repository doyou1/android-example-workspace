package com.example.chartsampling

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chartsampling.databinding.ActivityCircleChartBinding
import com.example.chartsampling.databinding.ActivityDefaultChartBinding
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*
import kotlin.streams.toList

class CircleChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCircleChartBinding
    private val itemSize = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCircleChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        setClickEvent()
        setChart()
    }

    private fun setClickEvent() {
        binding.btnReset.setOnClickListener {
            setChart()
        }
    }

    private fun setChart() {
        binding.chart.description.isEnabled = false

        binding.chart.centerText = "Circle Chart"
//        binding.chart.centerText = getSpannableString("Circle Chart")
        binding.chart.setCenterTextSize(25f)

        // radius of the center hole in percent of maximum radius
        binding.chart.holeRadius = 45f
        binding.chart.transparentCircleRadius = 50f


        binding.chart.legend.isEnabled = false
//        val l = binding.chart.legend
//        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
//        l.orientation = Legend.LegendOrientation.VERTICAL
//        l.setDrawInside(false)

        binding.chart.data = generateCircleData()

    }

    private fun generateCircleData(): PieData {
        val count = 4
        val entries = arrayListOf<PieEntry>()
        for (i in 0 until count) {
            entries.add(
                PieEntry(
                    ((Math.random() * 60) + 40).toFloat(), "Quarter${i + 1}"
                )
            )
        }
        val datasets = PieDataSet(entries, "Random Datasets")
        datasets.colors = ColorTemplate.VORDIPLOM_COLORS.toList()
        datasets.sliceSpace = 2f
        datasets.valueTextColor = Color.WHITE
        datasets.valueTextSize = 12f
        return PieData(datasets)
    }
}