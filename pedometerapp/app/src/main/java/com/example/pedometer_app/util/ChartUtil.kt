package com.example.pedometer_app.util

import android.graphics.Color
import com.github.mikephil.charting.data.*

class ChartUtil {
    companion object {
        // LineChart 관련 dataset, data 설정
        fun getLineData(lineEntries: ArrayList<Entry>) : LineData {
            val lineDataset = LineDataSet(lineEntries, "line chart")
            lineDataset.color = Color.GREEN
            lineDataset.circleRadius = 1f
            return LineData(lineDataset)
        }

        // BarChart 관련 dataset, data 설정
        fun getBarData(barEntries: ArrayList<BarEntry>) : BarData {
            val barDataset = BarDataSet(barEntries, "bar chart")
            barDataset.color = Color.BLUE
            return BarData(barDataset)
        }
    }
}