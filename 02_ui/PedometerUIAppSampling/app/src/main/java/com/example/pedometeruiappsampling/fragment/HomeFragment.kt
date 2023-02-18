package com.example.pedometeruiappsampling.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pedometeruiappsampling.adapter.WeekGoalAdapter
import com.example.pedometeruiappsampling.databinding.FragmentHomeBinding
import com.example.pedometeruiappsampling.util.DATA_WEEK_GOAL

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
        setWeekGoal()
        setChart()
        setText()
    }

    private fun setWeekGoal() {
        binding.rvWeekGoal.adapter = WeekGoalAdapter(DATA_WEEK_GOAL)
    }

    private fun setChart() {

//        binding.chartStep.

    }

    private fun setText() {
        binding.tvDistanceData.text = "12.7"
        binding.tvMinutesData.text = "152"
        binding.tvCaloriesData.text = "6913"
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