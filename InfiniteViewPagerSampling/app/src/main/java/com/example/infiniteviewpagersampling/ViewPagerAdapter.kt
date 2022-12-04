package com.example.infiniteviewpagersampling

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private var num = 0;
    private val TAG = this::class.java.simpleName

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        Log.e(TAG, "num: $num")
        return when(position) {
            0 -> {
                num--
                Fragment()
            }
            1 -> {
                ContentFragment(num)
            }
            2 -> {
                num++
                Fragment()
            }
            else -> throw NotImplementedError()
        }
    }
}