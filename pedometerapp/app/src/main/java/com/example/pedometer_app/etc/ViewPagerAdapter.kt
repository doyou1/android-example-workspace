package com.example.pedometer_app.etc

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pedometer_app.fragment.CommunityFragment
import com.example.pedometer_app.fragment.HistoryFragment
import com.example.pedometer_app.fragment.HomeFragment
import com.example.pedometer_app.util.FRAGMENT_COUNT

class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return FRAGMENT_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                HomeFragment.newInstance()
            }
            1 -> {
                CommunityFragment.newInstance()
            }
            2 -> {
                HistoryFragment.newInstance()
            }
            else -> {
                HomeFragment.newInstance()
            }
        }
    }
}