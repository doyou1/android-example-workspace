package com.example.chartsampling

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.chartsampling.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val itemSize = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
        val xvalue = arrayListOf<String>()
        val lineEntries = arrayListOf<Entry>()
        val barEntries = arrayListOf<BarEntry>()
        DateUtil.getStepModel(itemSize).forEachIndexed { i, stepModel ->
            xvalue.add(stepModel.date)
            lineEntries.add(Entry(i.toFloat(), stepModel.step.toFloat()))
            barEntries.add(BarEntry(i.toFloat(), stepModel.step.toFloat()))
        }

        val lineData = getLineData(lineEntries)
        val barData = getBarData(barEntries)
        // text above LineChart와 중복으로 barData text visible false
        barData.setValueTextSize(0f)
        val data = CombinedData()
        data.setData(lineData)
        data.setData(barData)

        binding.chart.data = data
        setChartConfig(xvalue)
    }

    // LineChart 관련 dataset, data 설정
    private fun getLineData(lineEntries: ArrayList<Entry>) : LineData {
        val lineDataset = LineDataSet(lineEntries, "line chart")
        lineDataset.color = Color.GREEN
        lineDataset.circleRadius = 1f
        return LineData(lineDataset)
    }

    // BarChart 관련 dataset, data 설정
    private fun getBarData(barEntries: ArrayList<BarEntry>) : BarData {
        val barDataset = BarDataSet(barEntries, "bar chart")
        barDataset.color = Color.BLUE
        return BarData(barDataset)
    }

    private fun setChartConfig(xvalue: ArrayList<String>) {
        binding.chart.setBackgroundColor(Color.WHITE)

        // Description hide
        binding.chart.description.isEnabled = false

        // chart draw 우선순위 지정(bar chart가 제일 밑)
        binding.chart.drawOrder = arrayOf(
            DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.CANDLE, DrawOrder.LINE, DrawOrder.SCATTER
        )

        // X축 Label을 Bottom Position
        binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        // X축 실선 hide
        binding.chart.xAxis.setDrawGridLines(false)

        binding.chart.xAxis.spaceMin = 0.5f
        binding.chart.xAxis.spaceMax = 0.5f

        binding.chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return xvalue[index]
            }
        }

        // x축을 기준으로 7개만 출력, 수평 스크롤 추가
        binding.chart.setVisibleXRangeMaximum(7f)
        // 가장 최근, 마지막, 제일 오른쪽 최초 표시
        binding.chart.moveViewToX(xvalue.size.toFloat())
    }
}