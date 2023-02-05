package com.example.pedometerclone.util

import java.util.*

class Util {
    companion object {
        fun getToday() : Long {
            val cal = Calendar.getInstance()
            cal.timeInMillis = System.currentTimeMillis()
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            return cal.timeInMillis
        }

        fun getTomorrow() : Long {
            val cal = Calendar.getInstance()
            cal.timeInMillis = System.currentTimeMillis()
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 1)
            cal.set(Calendar.MILLISECOND, 0)
            cal.add(Calendar.DATE, 1)
            return cal.timeInMillis
        }

    }
}