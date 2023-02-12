package com.example.chartsampling

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chartsampling.databinding.ActivityDefaultChartBinding
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter

class DefaultChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDefaultChartBinding
    private val itemSize = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefaultChartBinding.inflate(layoutInflater)
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
        val data = CombinedData()
        data.setData(lineData)
        data.setData(barData)
        binding.chart.data = data
        setChartConfig(xvalue)
    }

    // LineChart
    private fun getLineData(lineEntries: ArrayList<Entry>): LineData {
        val lineDataset = LineDataSet(lineEntries, "line chart")
        lineDataset.color = Color.GREEN
        lineDataset.lineWidth = 5f
        lineDataset.circleRadius = 1f
        lineDataset.valueTextSize = 20f
        return LineData(lineDataset)
    }

    // BarChart
    private fun getBarData(barEntries: ArrayList<BarEntry>): BarData {
        val barDataset = BarDataSet(barEntries, "bar chart")
        barDataset.color = Color.BLUE
        // barData text visible false because of lineData duplication
        barDataset.valueTextSize = 0f
        return BarData(barDataset)
    }

    private fun setChartConfig(xvalue: ArrayList<String>) {
        binding.chart.setBackgroundColor(Color.WHITE)

        // Description hide
        binding.chart.description.isEnabled = false

        // set chart draw order(bar chart is under most)
        binding.chart.drawOrder = arrayOf(
            CombinedChart.DrawOrder.BAR,
            CombinedChart.DrawOrder.BUBBLE,
            CombinedChart.DrawOrder.CANDLE,
            CombinedChart.DrawOrder.LINE,
            CombinedChart.DrawOrder.SCATTER
        )

        // XAxis Label set Bottom Position
        binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chart.xAxis.setDrawGridLines(false)
        binding.chart.xAxis.textSize = 14f
        binding.chart.xAxis.spaceMin = 0.5f
        binding.chart.xAxis.spaceMax = 0.5f
        binding.chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return xvalue[index]
            }
        }

        // xAxis print 7 item of 10 item, add horizontal scroll
        binding.chart.setVisibleXRangeMaximum(7f)
        // show most right, most last item
        binding.chart.moveViewToX(xvalue.size.toFloat())
    }
}