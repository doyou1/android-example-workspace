package com.example.numberpickersampling

import android.util.Log
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*
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
    fun findFirstMondayOfWeek() {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        println("${sdf.format(calendar.time)}")
        for(i: Int in 1..7) {
            calendar.set(Calendar.WEEK_OF_MONTH, i)
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONTH)

            val startDay = calendar.get(Calendar.DAY_OF_MONTH)
            val dayNum = calendar.get(Calendar.DAY_OF_WEEK)

            println("startDay $startDay dayNum $dayNum")

        }
    }
}