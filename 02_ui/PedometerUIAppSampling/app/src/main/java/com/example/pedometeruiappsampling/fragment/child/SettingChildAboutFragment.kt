package com.example.pedometeruiappsampling.fragment.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pedometeruiappsampling.databinding.FragmentSettingAboutBinding
import com.example.pedometeruiappsampling.databinding.FragmentSettingNotificationsBinding

class SettingChildAboutFragment : SettingChildBaseFragment() {

    private var _binding: FragmentSettingAboutBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private var instance: SettingChildBaseFragment? = null

        fun getInstance(): SettingChildBaseFragment {
            if (instance == null) {
                instance = SettingChildAboutFragment()
            }
            return instance!!
        }
    }
}