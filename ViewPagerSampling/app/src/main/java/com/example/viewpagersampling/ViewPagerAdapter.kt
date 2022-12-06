package com.example.viewpagersampling

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*
import kotlin.math.abs

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private val TAG = this::class.java.simpleName

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun createFragment(position: Int): Fragment {
        val calendar = Calendar.getInstance()
        // 月により、最後の日が違うので、予想以外月が変わる可能性があるため
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + (position - START_POSITION))
        val yyyy = calendar.get(Calendar.YEAR).toString()
        val mm = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
        val dd = if (isThisMonth(yyyy, mm)) getThisDayOfMonth() else TEXT_FIRST_DAY_OF_MONTH

        Log.e(TAG, "$yyyy$mm")

        return MonthFragment(
            yyyy,
            mm,
            if (isThisMonth(yyyy, mm)) getThisDayOfMonth() else TEXT_FIRST_DAY_OF_MONTH
        )
    }

    private fun isThisMonth(targetYear: String, targetMonth: String): Boolean {

        val calendar = Calendar.getInstance()
        val yyyy = calendar.get(Calendar.YEAR).toString()
        val mm = String.format("%02d", calendar.get(Calendar.MONTH) + 1)

        return targetYear == yyyy && targetMonth == mm
    }

    private fun getThisDayOfMonth(): String {
        val calendar = Calendar.getInstance()
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        return String.format("%02d", dayOfMonth)
    }

    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2
    }
}