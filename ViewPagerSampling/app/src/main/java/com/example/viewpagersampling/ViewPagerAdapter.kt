package com.example.viewpagersampling

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val YYYY: String, private val DD: String, fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return YEAR_IS_12_MONTHS
    }

    override fun createFragment(position: Int): Fragment {
        val MM = String.format("%02d", position +1)

        return MonthFragment("$YYYY$MM$DD")
    }
}