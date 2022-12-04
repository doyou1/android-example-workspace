package com.example.infiniteviewpagersampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.infiniteviewpagersampling.databinding.ActivityMainBinding

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
        binding.viewPager.adapter = ViewPagerAdapter(this)
        binding.viewPager.currentItem = 1
//        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                Log.e(TAG, "position: $position")
//                binding.viewPager.currentItem = 1
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//                super.onPageScrollStateChanged(state)
//                if(state == ViewPager2.SCROLL_STATE_IDLE)
//            }
//        })
    }
}