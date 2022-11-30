package com.example.pedometer_app.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pedometer_app.databinding.FragmentHistoryBinding
import com.example.pedometer_app.util.ChartUtil
import com.example.pedometer_app.util.DateUtil
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.lang.IndexOutOfBoundsException

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val TAG = this::class.java.simpleName

    private var itemSize = 7

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        /**
         * flag is true : Chart View
         * flag is false : Calendar View
         */
        binding.flag = true
        setChartLayout()
        setClickEvent()
    }

    private fun setChartLayout() {
        val xvalue = arrayListOf<String>()
        val lineEntries = arrayListOf<Entry>()
        val barEntries = arrayListOf<BarEntry>()

        DateUtil.getStepModel(itemSize).forEachIndexed { i, stepModel ->
            xvalue.add(stepModel.date)
            lineEntries.add(Entry(i.toFloat(), stepModel.step.toFloat()))
            barEntries.add(BarEntry(i.toFloat(), stepModel.step.toFloat()))
        }

        Log.e(TAG, "xvalue.size: ${xvalue.size}")

        val lineData = ChartUtil.getLineData(lineEntries)
        val barData = ChartUtil.getBarData(barEntries)
        // text above LineChart와 중복으로 barData text visible false
        barData.setValueTextSize(0f)
        val data = CombinedData()
        data.setData(lineData)
        data.setData(barData)

        binding.chart.data = data
        binding.chart.data.notifyDataChanged()
        binding.chart.notifyDataSetChanged()
        setChartConfig(xvalue)
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
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                // IndexOutOfBoundsException exception problem solve
                var result = "";
                try {
                    val index = value.toInt()
                    result = xvalue[index]
                } catch (e: IndexOutOfBoundsException) {
                    return ""
                }
                return result
            }
        }
        // x축을 기준으로 7개만 출력, 수평 스크롤 추가
        binding.chart.setVisibleXRangeMaximum(itemSize.toFloat())
        // 가장 최근, 마지막, 제일 오른쪽 최초 표시
//        binding.chart.moveViewToX(itemSize.toFloat())
//        binding.chart.moveViewToX(binding.chart.data.entryCount.toFloat())
    }

    private fun setClickEvent() {
        binding.btnToggleLayout.setOnClickListener {
            binding.flag = !binding.flag
        }
        /**
         * Chart Layout
         */
        binding.btnWeek.setOnClickListener {
            itemSize = 7
            binding.chart.clear()
            setChartLayout()
        }
        binding.btnMonth.setOnClickListener {
            itemSize = 30
            binding.chart.clear()
            setChartLayout()
        }
        binding.btnYear.setOnClickListener {
            itemSize = 365
            binding.chart.clear()
            setChartLayout()
            binding.chart.invalidate()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}