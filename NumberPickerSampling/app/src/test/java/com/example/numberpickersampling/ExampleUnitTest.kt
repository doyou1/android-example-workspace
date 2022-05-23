package com.example.numberpickersampling

import android.util.Log
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testCalendar() {
//        showFirstMondayByYear(2022)
//        println("======================")
//        showFirstMondayByYear(2021)
//        println("======================")
//        showFirstMondayByYear(2020)
//
//        println("======================")
//        println("======================")
//
//        showFirstMondayByYearAndMonth(2022, 8)
//        println("======================")
//        showFirstMondayByYearAndMonth(2022, 5)
//        println("======================")
//        showFirstMondayByYearAndMonth(2022, 4)
//        println("======================")
//        showFirstMondayByYearAndMonth(2022, 3)
//        println("======================")
//        showFirstMondayByYearAndMonth(2022, 2)
        println("======================")
        showFirstMondayByYearAndMonth(2022, 5)

    }

    fun showFirstMondayByYear(year: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        val oldYear = calendar.get(Calendar.YEAR)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        println("${sdf.format(calendar.time)}")
        for(i: Int in 0..11) {
            calendar.set(Calendar.MONTH, i)     // 0 : 1월
            calendar.set(Calendar.WEEK_OF_MONTH, 1)
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

            if (oldYear != calendar.get(Calendar.YEAR)) {
                // 1월달의 첫번째 월요일이 이전달에 들어있다면 연도가 바뀌게 됨.
                // 바뀐 연도가 다음 반복문에도 동일하게 적용되는 문제가 있음
                calendar.set(Calendar.YEAR, oldYear)
                calendar.set(Calendar.MONTH, i)     // 0 : 1월
                calendar.set(Calendar.WEEK_OF_MONTH, 2)
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            }

            if ((calendar.get(Calendar.MONTH) + 1) != (i+1)) {
                calendar.set(Calendar.MONTH, i)     // 0 : 1월
                calendar.set(Calendar.WEEK_OF_MONTH, 2)
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            }
            /*
            2021-12-27 : i-0, 1월 첫번째 월요일
            2021-02-01 : i-1, 2월 첫번째 월요일
            2021-03-01 : i-2, 3월 첫번째 월요일
            2021-03-29 : i-3, 4월 첫번째 월요일
            2021-04-26 : i-4, 5월 첫번째 월요일
            2021-05-31 : i-5, 6월 첫번째 월요일
            2021-06-28 : i-6, 7월 첫번째 월요일
            2021-08-02 : i-7, 8월 첫번째 월요일
            2021-08-30 : i-8, 9월 첫번째 월요일
            2021-09-27 : i-9, 10월 첫번째 월요일
            2021-11-01 : i-10, 11월 첫번째 월요일
            2021-11-29 : i-11, 12월 첫번째 월요일
             */
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)

            println("year: $year month: $month, day: $day weekOfMonth: $weekOfMonth ${sdf.format(calendar.time)}")
        }
    }
    fun showFirstMondayByYearAndMonth(year: Int, month: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)

        val oldYear = calendar.get(Calendar.YEAR)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        println("${sdf.format(calendar.time)}")

        calendar.set(Calendar.WEEK_OF_MONTH, 1)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        if (oldYear != calendar.get(Calendar.YEAR)) {
            // 1월달의 첫번째 월요일이 이전달에 들어있다면 연도가 바뀌게 됨.
            // 바뀐 연도가 다음 반복문에도 동일하게 적용되는 문제가 있음
            calendar.set(Calendar.YEAR, oldYear)
            calendar.set(Calendar.MONTH, month - 1)     // 0 : 1월
            calendar.set(Calendar.WEEK_OF_MONTH, 2)
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }

        if ((calendar.get(Calendar.MONTH) + 1) != (month)) {
            calendar.set(Calendar.MONTH, month - 1)     // 0 : 1월
            calendar.set(Calendar.WEEK_OF_MONTH, 2)
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)

        println("year: $year month: $month, day: $day weekOfMonth: $weekOfMonth ${sdf.format(calendar.time)}")

        showPeriod(calendar, year, month, day, weekOfMonth)
    }

    fun showPeriod(calendar: Calendar, year: Int, month: Int, day: Int, weekOfMonth: Int) {
        val list = ArrayList<String>()

        list.add("${day}~${day+6}")

        var newWeekOfMonth = weekOfMonth
        while (true) {
            newWeekOfMonth++
            calendar.set(Calendar.WEEK_OF_MONTH, newWeekOfMonth)

            if (month != calendar.get(Calendar.MONTH)) {
                println("day: ${calendar.get(Calendar.DAY_OF_MONTH)}")

                val dayOfnextMonday = calendar.get(Calendar.DAY_OF_MONTH)
                val dayOfSundayOfLastWeek = dayOfnextMonday - 1

                val index = list[list.size - 1].indexOf("~")
                list[list.size - 1] = "${list[list.size - 1].substring(0, index)}~$dayOfSundayOfLastWeek"
                break
            }

            val day = calendar.get(Calendar.DAY_OF_MONTH)
            list.add("${day}~${day+6}")
        }

        println(list)

    }
}