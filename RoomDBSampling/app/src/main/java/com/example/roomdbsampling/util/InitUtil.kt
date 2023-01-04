package com.example.roomdbsampling.util

import java.util.*
import kotlin.math.roundToInt

class InitUtil {

    companion object {
        fun getRandomDate(): String {
            val cal = Calendar.getInstance()

            val year = 2023
            cal.set(Calendar.YEAR, year)
            val dayOfYear = randBetween(1, cal.getActualMaximum(Calendar.DAY_OF_YEAR))
            cal.set(Calendar.DAY_OF_YEAR, dayOfYear)

            val yyyy = cal.get(Calendar.YEAR)
            val MM = String.format(
                "%02d",
                cal.get(Calendar.MONTH) + 1
            )
            val dd = String.format(
                "%02d",
                cal.get(Calendar.DAY_OF_MONTH)
            )
            val HH = String.format("%02d", randBetween(0, 23))
            val mm = String.format("%02d", randBetween(0, 59))

            return "$yyyy$MM$dd$HH$mm"
        }

        fun randBetween(start: Int, end: Int): Int {
            return start + (Math.random() * (end - start)).roundToInt()
        }
    }
}