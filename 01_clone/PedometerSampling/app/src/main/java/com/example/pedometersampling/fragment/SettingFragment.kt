package com.example.pedometersampling.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pedometersampling.databinding.FragmentSettingBinding
import com.example.pedometersampling.room.Pedometer
import com.example.pedometersampling.util.Util

class SettingFragment : BaseFragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(requireContext(), "Setting Fragment", Toast.LENGTH_LONG).show()
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
        private var instance: SettingFragment? = null
        fun getInstance(): SettingFragment {
            if (instance == null) {
                instance = SettingFragment()
            }
            return instance!!
        }
    }
}