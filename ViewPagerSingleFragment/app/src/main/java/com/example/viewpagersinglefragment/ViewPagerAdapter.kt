package com.example.viewpagersinglefragment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val TAG = this::class.java.simpleName
    private var num = 0

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        Log.e(TAG, "createdFragment: $position")
        return TestFragment(num++)
    }




}