package com.example.pedometeruiappsampling.fragment.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.pedometeruiappsampling.R
import com.example.pedometeruiappsampling.databinding.FragmentSettingHomeBinding
import com.example.pedometeruiappsampling.fragment.SettingFragment

class SettingChildHomeFragment : SettingChildBaseFragment() {

    private var _binding: FragmentSettingHomeBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
    }

    private fun setClickEvent() {

        binding.btnNotifications.setOnClickListener {
            SettingFragment.setChildInstance(SettingChildNotificationsFragment.getInstance())
            Navigation.findNavController(requireActivity(), R.id.fc_view).navigate(R.id.action_home_to_notifications)
        }
        binding.btnPrivacySecurity.setOnClickListener {
            SettingFragment.setChildInstance(SettingChildPrivacySecurityFragment.getInstance())
            Navigation.findNavController(requireActivity(), R.id.fc_view).navigate(R.id.action_home_to_privacy_security)
        }
        binding.btnHelpSupport.setOnClickListener {
            SettingFragment.setChildInstance(SettingChildHelpAndSupportFragment.getInstance())
            Navigation.findNavController(requireActivity(), R.id.fc_view).navigate(R.id.action_home_to_help_and_support)
        }
        binding.btnAbout.setOnClickListener {
            SettingFragment.setChildInstance(SettingChildAboutFragment.getInstance())
            Navigation.findNavController(requireActivity(), R.id.fc_view).navigate(R.id.action_home_to_about)
        }
    }

    companion object {
        private var instance: SettingChildBaseFragment? = null

        fun getInstance(): SettingChildBaseFragment {
            if (instance == null) {
                instance = SettingChildHomeFragment()
            }
            return instance!!
        }
    }
}