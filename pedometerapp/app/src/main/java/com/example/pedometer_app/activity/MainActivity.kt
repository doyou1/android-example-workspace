package com.example.pedometer_app.activity

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.example.pedometer_app.util.COMMUNITY
import com.example.pedometer_app.util.HISTORY
import com.example.pedometer_app.util.HOME
import com.example.pedometer_app.etc.ViewPagerAdapter
import com.example.pedometer_app.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = HOME
                }
                1 -> {
                    tab.text = COMMUNITY
                }
                2 -> {
                    tab.text = HISTORY
                }
            }
        }.attach()
    }

//    private val TAG: String = this::class.java.simpleName
//    private val handler = Handler(Looper.getMainLooper())
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        checkActivityPermission()
//    }
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    fun checkActivityPermission() {
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
//            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        resetPedometerService()
//        resetSharedPreferencesChangeListener()
//    }
//
//    private fun resetPedometerService() {
//        val intent = Intent(this, PedometerService::class.java)
//        stopService(intent)
//        startService(intent)
//    }
//
//    private fun resetSharedPreferencesChangeListener() {
//        handler.removeCallbacksAndMessages(null)
//        setStepInfinite()
//    }
//
//    private fun setStepInfinite() {
//        val prefs = getSharedPreferences("step", Context.MODE_PRIVATE)
//        binding.step = prefs.getInt("value", 0)
//
//        // 5초마다
//        handler.postDelayed(::setStepInfinite, 1000 * 5)
//    }


}