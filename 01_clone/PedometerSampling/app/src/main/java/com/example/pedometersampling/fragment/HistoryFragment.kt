package com.example.pedometersampling.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pedometersampling.databinding.FragmentHistoryBinding
import com.example.pedometersampling.room.Pedometer
import com.example.pedometersampling.util.Util

class HistoryFragment : BaseFragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

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
        Toast.makeText(requireContext(), "History Fragment", Toast.LENGTH_LONG).show()
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
        private var instance: HistoryFragment? = null
        fun getInstance(): HistoryFragment {
            if (instance == null) {
                instance = HistoryFragment()
            }
            return instance!!
        }
    }
}