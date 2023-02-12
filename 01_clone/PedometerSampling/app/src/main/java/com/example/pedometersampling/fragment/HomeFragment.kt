package com.example.pedometersampling.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pedometersampling.databinding.FragmentHomeBinding
import com.example.pedometersampling.room.Pedometer
import com.example.pedometersampling.util.ChartUtil
import com.example.pedometersampling.util.GOAL_STEPS
import com.example.pedometersampling.util.Util

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

    override fun updateCurrentSteps(item: Pedometer?) {
        super.updateCurrentSteps(item)
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
        binding.chart.data = ChartUtil.generatePieData(item)
        binding.chart.invalidate()
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