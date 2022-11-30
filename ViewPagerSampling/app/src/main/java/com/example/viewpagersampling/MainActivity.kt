package com.example.viewpagersampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viewpagersampling.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ViewPageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ViewPageAdapter(this)
        binding.pager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
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
}