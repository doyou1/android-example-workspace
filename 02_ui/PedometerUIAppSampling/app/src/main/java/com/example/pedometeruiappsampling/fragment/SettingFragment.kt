package com.example.pedometeruiappsampling.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pedometeruiappsampling.databinding.FragmentSettingBinding

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