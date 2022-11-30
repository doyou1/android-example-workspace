package com.example.viewpagersampling

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPageAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
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