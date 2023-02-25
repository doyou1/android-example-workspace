package com.example.pedometeruiappsampling.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.pedometeruiappsampling.R
import com.example.pedometeruiappsampling.databinding.FragmentSettingBinding
import com.example.pedometeruiappsampling.fragment.child.SettingChildBaseFragment
import com.example.pedometeruiappsampling.fragment.child.SettingChildHomeFragment

class SettingFragment : BaseFragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        private var instance: SettingFragment? = null
        private var childInstance: SettingChildBaseFragment? = null

        fun getInstance(): SettingFragment {
            if (instance == null) {
                instance = SettingFragment()
            }
            return instance!!
        }

        fun setChildInstance(current: SettingChildBaseFragment) {
            childInstance = current
        }

        fun getChildInstance(): SettingChildBaseFragment {
            if (childInstance == null) {
                childInstance = SettingChildHomeFragment()
            }
            return childInstance!!
        }
    }
}