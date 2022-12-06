package com.example.multifragmentinsingleactivitysampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.multifragmentinsingleactivitysampling.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
    private var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        initTabLayout()
        initDate()
        initFrameLayout()
        setClickEvent()
    }

    private fun initTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("tab1"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("tab2"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("tab3"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("tab4"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("tab5"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                setFragment(tab?.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                setFragment(tab?.position)
            }
        })
    }

    private fun initFrameLayout() {
        setFragment(0)
    }

    private fun initDate() {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMdd")
        currentDate = sdf.format(calendar.time)
        binding.tvDate.text = currentDate
    }

    private fun setClickEvent() {
        binding.btnPrev.setOnClickListener {
            currentDate?.let {
                val yyyy = it.substring(0, 4).toInt()
                val MM = it.substring(4, 6).toInt() - 1
                val dd = it.substring(6, 8).toInt()

                val cal = Calendar.getInstance()
                cal.set(yyyy, MM, dd)
                cal.set(Calendar.DAY_OF_MONTH, dd - 1)
                val sdf = SimpleDateFormat("yyyyMMdd")
                currentDate = sdf.format(cal.time)
                binding.tvDate.text = currentDate

                val currentPosition = binding.tabLayout.selectedTabPosition
                binding.tabLayout.getTabAt(currentPosition)?.select()
            }
        }

        binding.btnNext.setOnClickListener {
            currentDate?.let {
                val yyyy = it.substring(0, 4).toInt()
                val MM = it.substring(4, 6).toInt() - 1
                val dd = it.substring(6, 8).toInt()

                val cal = Calendar.getInstance()
                cal.set(yyyy, MM, dd)
                cal.set(Calendar.DAY_OF_MONTH, dd + 1)
                val sdf = SimpleDateFormat("yyyyMMdd")
                currentDate = sdf.format(cal.time)
                binding.tvDate.text = currentDate

                val currentPosition = binding.tabLayout.selectedTabPosition
                binding.tabLayout.getTabAt(currentPosition)?.select()
            }
        }
    }

    private fun setFragment(position: Int?) {
        position?.let {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = when(it) {
                0 -> FirstFragment(currentDate)
                1 -> SecondFragment(currentDate)
                2 -> ThirdFragment(currentDate)
                3 -> FourthFragment(currentDate)
                4 -> FifthFragment(currentDate)
                else -> throw NotImplementedError()
            }
            transaction.replace(binding.frameLayout.id, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}