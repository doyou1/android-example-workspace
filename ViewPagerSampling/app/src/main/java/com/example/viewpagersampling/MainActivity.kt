package com.example.viewpagersampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpagersampling.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setViewPager()
    }

    private fun setViewPager() {
        val sharedPreferences = getSharedPreferences(TEXT_TODAY, MODE_PRIVATE)
        val today = sharedPreferences.getString(TEXT_TODAY, TEXT_EMPTY)
        if(today != TEXT_EMPTY) {
            val YYYY = today!!.substring(0, 4)
            val MM = today!!.substring(4, 6)
            val DD = today!!.substring(6, 8)
            binding.pager.adapter = ViewPagerAdapter(YYYY, DD, this)

            val position = MM.toInt() - 1
            binding.pager.currentItem = position

            TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
                tab.text = "${position+1}"
            }.attach()
        }
    }
}