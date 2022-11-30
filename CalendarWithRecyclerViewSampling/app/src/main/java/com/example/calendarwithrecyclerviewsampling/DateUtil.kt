package com.example.calendarwithrecyclerviewsampling

import java.util.*

class DateUtil {
    companion object {
        private val TAG = this::class.java.simpleName


        fun getDateList() : ArrayList<String> {
            val dateList = arrayListOf<String>()

            var calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val dayOfWeekByMonthFirstDay = calendar.get(Calendar.DAY_OF_WEEK)

            // 달력은 첫번째 날 설정(1열 1행)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - (dayOfWeekByMonthFirstDay - 1))

            for (i in 0 until 35) {
                val _year = calendar.get(Calendar.YEAR)
                val _month = if((calendar.get(Calendar.MONTH) + 1) < 10) "0${calendar.get(Calendar.MONTH) + 1}"
                            else calendar.get(Calendar.MONTH) + 1
                val _day = if((calendar.get(Calendar.DAY_OF_MONTH)) < 10) "0${calendar.get(Calendar.DAY_OF_MONTH)}"
                            else calendar.get(Calendar.DAY_OF_MONTH)

                dateList.add("$_year$_month$_day")

                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
            }
            return dateList
        }
    }
}