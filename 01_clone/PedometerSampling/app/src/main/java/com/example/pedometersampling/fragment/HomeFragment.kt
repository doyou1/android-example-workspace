package com.example.pedometersampling.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pedometersampling.databinding.FragmentHomeBinding
import com.example.pedometersampling.room.Pedometer
import com.example.pedometersampling.util.Util

class HomeFragment : BaseFragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        Toast.makeText(requireContext(), "Home Fragment", Toast.LENGTH_LONG).show()
    }

    override fun updateUI(item: Pedometer?) {
        super.updateUI(item)
        if (item == null) {
            binding.tvContent.text = "steps: 0"
        } else {
            binding.tvContent.text =
                "steps: ${Util.computeSteps(item!!)} \n" +
                        "${Util.stepsToString(item!!)}"
        }
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