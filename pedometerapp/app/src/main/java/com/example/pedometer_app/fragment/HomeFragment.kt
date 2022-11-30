package com.example.pedometer_app.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.pedometer_app.service.PedometerService
import com.example.pedometer_app.util.SECONDS_5
import com.example.pedometer_app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        checkActivityPermission()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkActivityPermission() {
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)

            binding.tvPermission.text = "No Pedometer Pemission"
        }
        else {
            binding.tvPermission.text = "Have Pedometer Pemission"
        }
    }

    override fun onResume() {
        super.onResume()
        resetPedometerService()
        resetSharedPreferencesChangeListener()
   }

    private fun resetPedometerService() {
        val intent = Intent(requireContext(), PedometerService::class.java)
        activity?.stopService(intent)
        activity?.startService(intent)
    }

    private fun resetSharedPreferencesChangeListener() {
        handler.removeCallbacksAndMessages(null)
        setStepInfinite()
    }

    private fun setStepInfinite() {
        val prefs = activity?.getSharedPreferences("step", Context.MODE_PRIVATE)
        prefs?.let {
            binding.step = it.getInt("value", 0)
            handler.postDelayed(::setStepInfinite, SECONDS_5)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}