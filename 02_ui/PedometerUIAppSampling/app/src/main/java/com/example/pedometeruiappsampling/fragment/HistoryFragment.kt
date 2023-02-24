package com.example.pedometeruiappsampling.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pedometeruiappsampling.R
import com.example.pedometeruiappsampling.databinding.FragmentHistoryBinding
import com.example.pedometeruiappsampling.util.*
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.model.GradientColor

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
        setChartDaily()
        setChartDailyAverageLine()
        binding.chartDaily.setOnTouchListener { _, _ ->
            setChartDailyAverageLine()
            false
        }

        setChartWeek()
        setChartWeekAverageLine()

    }

    private fun setChartDaily() {
        val xvalue = Util.getChartDailyXValue()
        val barDataset = Util.getChartDailyDataSet(xvalue.size)
        // gradient bar color
        barDataset.gradientColors = listOf(
            GradientColor(
                resources.getColor(R.color.app_color),
                resources.getColor(R.color.app_orange)
            )
        )

        // barData text visible false because of lineData duplication
        val data = BarData(barDataset)
        data.barWidth = SIZE_BAR_WIDTH
        data.isHighlightEnabled = false
        binding.chartDaily.data = data

        binding.chartDaily.description.isEnabled = false
        binding.chartDaily.legend.isEnabled = false
        binding.chartDaily.setScaleEnabled(false)
        binding.chartDaily.isDoubleTapToZoomEnabled = false

        val goal = if (context != null && context?.getSharedPreferences(
                TEXT_GOAL,
                Context.MODE_PRIVATE
            ) != null
        ) {
            context?.getSharedPreferences(TEXT_GOAL, Context.MODE_PRIVATE)!!
                .getInt(TEXT_GOAL, DEFAULT_GOAL)
        } else {
            DEFAULT_GOAL
        }
        val goalLine = LimitLine(goal.toFloat(), TEXT_GOAL)
        goalLine.lineWidth = SIZE_LINE_WIDTH
        goalLine.textSize = SIZE_GOAL_LINE
        goalLine.lineColor = resources.getColor(R.color.app_color)
        goalLine.textColor = resources.getColor(R.color.app_color)
        binding.chartDaily.axisLeft.addLimitLine(goalLine)
        binding.chartDaily.axisLeft.setDrawAxisLine(false)
        binding.chartDaily.axisLeft.setDrawGridLines(false)
        binding.chartDaily.axisLeft.axisMinimum = 0f
        binding.chartDaily.axisLeft.axisMaximum = (goal + (goal / 10)).toFloat()
        binding.chartDaily.axisRight.isEnabled = false

        val chartRenderer = RoundBarChartRender(
            binding.chartDaily,
            binding.chartDaily.animator,
            binding.chartDaily.viewPortHandler
        )
        chartRenderer.setRadius(20)
        binding.chartDaily.renderer = chartRenderer
        binding.chartDaily.xAxis.textColor = resources.getColor(R.color.app_color)
        binding.chartDaily.xAxis.spaceMin = SIZE_SPACE
        binding.chartDaily.xAxis.spaceMax = SIZE_SPACE
        binding.chartDaily.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return xvalue[index]
            }
        }
        binding.chartDaily.xAxis.setDrawGridLines(false)
        binding.chartDaily.xAxis.setDrawAxisLine(false)
        binding.chartDaily.xAxis.textSize = TEXT_SIZE_X_AXIS
        binding.chartDaily.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chartDaily.extraBottomOffset = EXTRA_BOTTOM_OFFSET
        // xAxis print 6 item of one time, add horizontal scroll
        binding.chartDaily.setVisibleXRangeMaximum(SIZE_X_RANGE_MAXIMUM)
        // show most right, most last item
        binding.chartDaily.moveViewToX(xvalue.size.toFloat())
        binding.chartDaily.invalidate()
    }

    private fun setChartDailyAverageLine() {
        var average = 0
        val minIdx = binding.chartDaily.lowestVisibleX.toInt()
        val maxIdx = binding.chartDaily.highestVisibleX.toInt()
        for (i in minIdx..maxIdx) {
            val item = binding.chartDaily.data.dataSets[0].getEntryForIndex(i)
            average += item.y.toInt()
        }
        average = (average / (maxIdx - minIdx))
        val averageLine = LimitLine(average.toFloat(), TEXT_AVERAGE)
        averageLine.lineWidth = SIZE_LINE_WIDTH
        averageLine.textSize = TEXT_SIZE_AVERAGE_LINE
        averageLine.lineColor = resources.getColor(R.color.purple_700)
        averageLine.textColor = resources.getColor(R.color.purple_700)

        binding.chartDaily.axisLeft.limitLines.forEach { ll ->
            if (ll.label == TEXT_AVERAGE) {
                binding.chartDaily.axisLeft.removeLimitLine(ll)
            }
        }
        binding.chartDaily.axisLeft.addLimitLine(averageLine)
    }

    private fun setChartWeek() {

    }

    private fun setChartWeekAverageLine() {

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