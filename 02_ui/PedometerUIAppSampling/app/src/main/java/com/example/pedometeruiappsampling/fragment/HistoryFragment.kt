package com.example.pedometeruiappsampling.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pedometeruiappsampling.R
import com.example.pedometeruiappsampling.databinding.FragmentHistoryBinding
import com.example.pedometeruiappsampling.util.RoundBarChartRender
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.model.GradientColor
import kotlin.random.Random

class HistoryFragment : BaseFragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setChart()
    }

    private fun setChart() {
        val xvalue = arrayListOf<String>()
        xvalue.add("1")
        xvalue.add("2")
        xvalue.add("3")
        xvalue.add("4")
        xvalue.add("5")
        xvalue.add("6")

        xvalue.add("11/1")
        xvalue.add("11/2")
        xvalue.add("11/3")
        xvalue.add("11/4")
        xvalue.add("11/5")
        xvalue.add("Today")
        val barEntries = arrayListOf<BarEntry>()
        barEntries.add(BarEntry(0f, Random.nextInt(0, 10000).toFloat()))
        barEntries.add(BarEntry(1f, Random.nextInt(0, 10000).toFloat()))
        barEntries.add(BarEntry(2f, Random.nextInt(0, 10000).toFloat()))
        barEntries.add(BarEntry(3f, Random.nextInt(0, 10000).toFloat()))
        barEntries.add(BarEntry(4f, Random.nextInt(0, 10000).toFloat()))
        barEntries.add(BarEntry(5f, Random.nextInt(0, 10000).toFloat()))

        barEntries.add(BarEntry(6f, Random.nextInt(0, 10000).toFloat()))
        barEntries.add(BarEntry(7f, Random.nextInt(0, 10000).toFloat()))
        barEntries.add(BarEntry(8f, Random.nextInt(0, 10000).toFloat()))
        barEntries.add(BarEntry(9f, Random.nextInt(0, 10000).toFloat()))
        barEntries.add(BarEntry(10f, Random.nextInt(0, 10000).toFloat()))
        barEntries.add(BarEntry(11f, Random.nextInt(0, 10000).toFloat()))

        val barDataset = BarDataSet(barEntries, "bar chart")
//        barDataset.color = resources.getColor(R.color.app_color)
        barDataset.gradientColors = listOf(
            GradientColor(
                resources.getColor(R.color.app_color),
                resources.getColor(R.color.app_orange)
            )
        )
        // barData text visible false because of lineData duplication
        val data = BarData(barDataset)
        data.barWidth = 0.25f
        data.isHighlightEnabled = false
        binding.chartHistory.data = data


        binding.chartHistory.description.isEnabled = false
        binding.chartHistory.legend.isEnabled = false
        binding.chartHistory.setScaleEnabled(false)
        binding.chartHistory.isDoubleTapToZoomEnabled = false

        binding.chartHistory.axisLeft.isEnabled = false
        binding.chartHistory.axisRight.isEnabled = false

        val chartRenderer = RoundBarChartRender(
            binding.chartHistory,
            binding.chartHistory.animator,
            binding.chartHistory.viewPortHandler
        )
        chartRenderer.setRadius(20)
        binding.chartHistory.renderer = chartRenderer

        binding.chartHistory.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chartHistory.xAxis.setDrawGridLines(false)
        binding.chartHistory.xAxis.setDrawAxisLine(false)
        binding.chartHistory.xAxis.textSize = 12f
        binding.chartHistory.xAxis.yOffset = -10f
        binding.chartHistory.xAxis.textColor = resources.getColor(R.color.app_color)
        binding.chartHistory.xAxis.spaceMin = 0.5f
        binding.chartHistory.xAxis.spaceMax = 0.5f
        binding.chartHistory.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return xvalue[index]
            }
        }

        // xAxis print 7 item of 10 item, add horizontal scroll
        binding.chartHistory.setVisibleXRangeMaximum(6f)
        // show most right, most last item
        binding.chartHistory.moveViewToX(xvalue.size.toFloat())
    }

    companion object {
        private var instance: HistoryFragment? = null
        fun getInstance(): HistoryFragment {
            if (instance == null) {
                instance = HistoryFragment()
            }
            return instance!!
        }
    }
}