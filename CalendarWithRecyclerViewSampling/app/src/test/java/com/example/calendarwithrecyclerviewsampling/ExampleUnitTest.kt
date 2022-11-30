package com.example.calendarwithrecyclerviewsampling

import android.util.Log
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//    }

    val yearMonth = "202204"
    @Test
    fun dateTest() {
        val dateList = arrayListOf<String>()

        var calendar = Calendar.getInstance()
        val _year = yearMonth.substring(0, 4)
        val _month = yearMonth.substring(4, 6)
        calendar.set(Calendar.YEAR, _year.toInt())
        calendar.set(Calendar.MONTH, _month.toInt() - 1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val dayOfWeekByMonthFirstDay = calendar.get(Calendar.DAY_OF_WEEK)
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

        println(dateList)
    }

    fun getDateList() : ArrayList<String> {

        val dateList = arrayListOf<String>()

        /**
         * Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
         *
         * 1 -> 일
         * 2 -> 월
         * 3 -> 화
         * 4 -> 수
         * 5 -> 목
         * 6 -> 금
         * 7 -> 토
         */

        var calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        /**
         * dayOfWeekByMonthFirstDay이 1이면
         * 달력에 이전달을 필요 없음
         *
         * dayOfWeekByMonthFirstDay이 2이면
         * 1일
         *
         * dayOfWeekByMonthFirstDay이 3이면
         * 2일
         *
         * ...
         *
         * dayOfWeekByMonthFirstDay이 7이면
         * 6일
         */
        val dayOfWeekByMonthFirstDay = calendar.get(Calendar.DAY_OF_WEEK)
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

//            for (i in 0 until dayOfWeekByMonthFirstDay-1) {
//                // dayOfWeekByMonthFirstDay: 3
//                // i: 0, -(dayOfWeekByMonthFirstDay - i - 1) = -(3 - 0 - 1), expect minus: -2
//                // i: 1, -(dayOfWeekByMonthFirstDay - i - 1) = -(3 - 1 - 1),expect minus: -1
//                calendar.set(Calendar.DAY_OF_MONTH, -(dayOfWeekByMonthFirstDay - i - 1))
//
//                val _year = calendar.get(Calendar.YEAR)
//                val _month = if((calendar.get(Calendar.MONTH) + 1) < 10) "0${calendar.get(Calendar.MONTH) + 1}"
//                            else calendar.get(Calendar.MONTH) + 1
//                val _day = if((calendar.get(Calendar.DAY_OF_MONTH)) < 10) "0${calendar.get(Calendar.DAY_OF_MONTH)}"
//                            else calendar.get(Calendar.DAY_OF_MONTH)
//
//                dateList.add("$_year$_month$_day")
//            }
//
//            // 다시 현재달 calendar instance 할당
//            calendar = Calendar.getInstance()
//            calendar.set(Calendar.DAY_OF_MONTH, 1)
//            val changeMonthFlag = calendar.get(Calendar.MONTH)
//            while (true) {
//                if(changeMonthFlag != calendar.get(Calendar.MONTH)) break
//
//                val _year = calendar.get(Calendar.YEAR)
//                val _month = if((calendar.get(Calendar.MONTH) + 1) < 10) "0${calendar.get(Calendar.MONTH) + 1}"
//                else calendar.get(Calendar.MONTH) + 1
//                val _day = if((calendar.get(Calendar.DAY_OF_MONTH)) < 10) "0${calendar.get(Calendar.DAY_OF_MONTH)}"
//                else calendar.get(Calendar.DAY_OF_MONTH)
//                dateList.add("$_year$_month$_day")
//                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
//            }
//
//            for(i in 0 until 3) {
//                val _year = calendar.get(Calendar.YEAR)
//                val _month = if((calendar.get(Calendar.MONTH) + 1) < 10) "0${calendar.get(Calendar.MONTH) + 1}"
//                else calendar.get(Calendar.MONTH) + 1
//                val _day = if((calendar.get(Calendar.DAY_OF_MONTH)) < 10) "0${calendar.get(Calendar.DAY_OF_MONTH)}"
//                else calendar.get(Calendar.DAY_OF_MONTH)
//                dateList.add("$_year$_month$_day")
//                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
//            }

        return dateList
    }

}