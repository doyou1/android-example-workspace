package com.example.pedometersampling.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pedometersampling.databinding.FragmentHomeBinding
import com.example.pedometersampling.room.Pedometer
import com.example.pedometersampling.util.GOAL_STEPS
import com.example.pedometersampling.util.Util
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun updateUI(item: Pedometer?) {
        super.updateUI(item)
        updateChart(item)
    }

    private fun updateChart(item: Pedometer?) {
        binding.chart.description.isEnabled = false
        binding.chart.centerText = if (item == null) {
            "0 / $GOAL_STEPS"
        } else {
            "${Util.computeSteps(item)} / $GOAL_STEPS"
        }
        binding.chart.setCenterTextSize(25f)
        binding.chart.holeRadius = 45f
        binding.chart.transparentCircleRadius = 50f
        binding.chart.legend.isEnabled = false
        binding.chart.data = generateData(item)

        binding.chart.invalidate()
    }

    private fun generateData(item: Pedometer?): PieData {
        val entries = arrayListOf<PieEntry>()

        val steps = if (item == null) 0 else Util.computeSteps(item)
        entries.add(PieEntry(steps.toFloat(), "current steps"))
        val goal = if (steps > GOAL_STEPS) 0 else GOAL_STEPS - steps
        entries.add(PieEntry(goal.toFloat(), "steps to reach for goal"))

        val datasets = PieDataSet(entries, "steps")
        datasets.colors = ColorTemplate.VORDIPLOM_COLORS.toList()
        datasets.sliceSpace = 2f
        datasets.valueTextColor = Color.WHITE
        datasets.valueTextSize = 24f
        return PieData(datasets)
    }

    companion object {
        private var instance: HomeFragment? = null
        fun getInstance(): HomeFragment {
            if (instance == null) {
                instance = HomeFragment()
            }
            return instance!!
        }
    }
}