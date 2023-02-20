package com.example.pedometeruiappsampling.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pedometeruiappsampling.R
import com.example.pedometeruiappsampling.databinding.FragmentHistoryBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
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

        val barDataset = BarDataSet(barEntries, "bar chart")
        barDataset.color = resources.getColor(R.color.app_color)
        // barData text visible false because of lineData duplication
        barDataset.valueTextSize = 0f
        val data = BarData(barDataset)
        binding.chartHistory.data = data

        binding.chartHistory.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chartHistory.xAxis.setDrawGridLines(false)
        binding.chartHistory.xAxis.textSize = 14f
        binding.chartHistory.xAxis.textColor = resources.getColor(R.color.app_color)
        binding.chartHistory.xAxis.spaceMin = 0.5f
        binding.chartHistory.xAxis.spaceMax = 0.5f
        binding.chartHistory.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return xvalue[index]
            }
        }
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