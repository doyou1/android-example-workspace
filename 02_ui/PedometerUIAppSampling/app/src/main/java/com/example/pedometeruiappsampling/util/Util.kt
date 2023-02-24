package com.example.pedometeruiappsampling.util

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class Util {

    companion object {

        fun getChartDailyXValue() : List<String> {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("MM/dd")
            val today = sdf.format(cal.time)
            cal.add(Calendar.MONTH, -1)
            val xvalue = arrayListOf<String>()
            while(true) {
                val date = sdf.format(cal.time)
                if(today != date) {
                    xvalue.add(date)
                } else {
                    xvalue.add(TEXT_TODAY)
                    break
                }
                cal.add(Calendar.DAY_OF_MONTH, 1)
            }

            return xvalue
        }

        fun getChartDailyDataSet(size: Int) : BarDataSet {
            val barEntries = arrayListOf<BarEntry>()
            for (i in 0 until size) {
                barEntries.add(BarEntry(i.toFloat(), Random.nextInt(0, 10000).toFloat()))
            }
            return BarDataSet(barEntries, TEXT_BAR_CHART)
        }
    }
}