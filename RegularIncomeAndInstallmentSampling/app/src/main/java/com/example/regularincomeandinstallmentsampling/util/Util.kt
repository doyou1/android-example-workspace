package com.example.regularincomeandinstallmentsampling.util

import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object {
        private val TAG = this::class.java.simpleName

        fun getPeriod(flag: Int): String {
            return when (flag) {
                FLAG_NONE -> TEXT_NONE
                FLAG_EVERY_DAY -> TEXT_EVERY_DAY
                FLAG_EVERY_WEEK -> TEXT_EVERY_WEEK
                FLAG_EVERY_TWO_WEEKS -> TEXT_EVERY_TWO_WEEKS
                FLAG_EVERY_FOUR_WEEKS -> TEXT_EVERY_FOUR_WEEKS
                FLAG_EVERY_MONTH -> TEXT_EVERY_MONTH
                FLAG_THE_END_OF_THE_MONTH -> TEXT_THE_END_OF_THE_MONTH
                FLAG_EVERY_TWO_MONTHS -> TEXT_EVERY_TWO_MONTHS
                FLAG_EVERY_THREE_MONTHS -> TEXT_EVERY_THREE_MONTHS
                FLAG_EVERY_FOUR_MONTHS -> TEXT_EVERY_FOUR_MONTHS
                FLAG_EVERY_SIX_MONTHS -> TEXT_EVERY_SIX_MONTHS
                FLAG_EVERY_YEAR -> TEXT_EVERY_YEAR
                else -> throw NotImplementedError()
            }
        }

        fun getPeriod(period: String): Int {
            return when (period) {
                TEXT_NONE -> FLAG_NONE
                TEXT_EVERY_DAY -> FLAG_EVERY_DAY
                TEXT_EVERY_WEEK -> FLAG_EVERY_WEEK
                TEXT_EVERY_TWO_WEEKS -> FLAG_EVERY_TWO_WEEKS
                TEXT_EVERY_FOUR_WEEKS -> FLAG_EVERY_FOUR_WEEKS
                TEXT_EVERY_MONTH -> FLAG_EVERY_MONTH
                TEXT_THE_END_OF_THE_MONTH -> FLAG_THE_END_OF_THE_MONTH
                TEXT_EVERY_TWO_MONTHS -> FLAG_EVERY_TWO_MONTHS
                TEXT_EVERY_THREE_MONTHS -> FLAG_EVERY_THREE_MONTHS
                TEXT_EVERY_FOUR_MONTHS -> FLAG_EVERY_FOUR_MONTHS
                TEXT_EVERY_SIX_MONTHS -> FLAG_EVERY_SIX_MONTHS
                TEXT_EVERY_YEAR -> FLAG_EVERY_YEAR
                else -> throw NotImplementedError()
            }
        }

        fun getToday(): String {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyyMMdd")
            return sdf.format(cal.time)
        }

        fun getPlusDate(flag: Int): String {
            return when (flag) {
                FLAG_EVERY_DAY -> PLUS_TIME_EVERY_DAY
                FLAG_EVERY_WEEK -> PLUS_TIME_EVERY_WEEK
                FLAG_EVERY_TWO_WEEKS -> PLUS_TIME_EVERY_TWO_WEEKS
                FLAG_EVERY_FOUR_WEEKS -> PLUS_TIME_EVERY_FOUR_WEEKS
                FLAG_EVERY_MONTH -> PLUS_TIME_EVERY_MONTH
                FLAG_THE_END_OF_THE_MONTH -> PLUS_TIME_THE_END_OF_THE_MONTH
                FLAG_EVERY_TWO_MONTHS -> PLUS_TIME_EVERY_TWO_MONTHS
                FLAG_EVERY_THREE_MONTHS -> PLUS_TIME_EVERY_THREE_MONTHS
                FLAG_EVERY_FOUR_MONTHS -> PLUS_TIME_EVERY_FOUR_MONTHS
                FLAG_EVERY_SIX_MONTHS -> PLUS_TIME_EVERY_SIX_MONTHS
                FLAG_EVERY_YEAR -> PLUS_TIME_EVERY_YEAR
                else -> throw NotImplementedError()
            }
        }

        fun getTargetDate(prevDate: String, plusDate: String): String {
            if (plusDate == TEXT_THE_END_OF_THE_MONTH) {
                val prevMonth = prevDate.substring(4, 6).toInt()
                val plusMonth = 1

                val targetYear = prevDate.substring(0, 4)
                val targetMonth = String.format("%02d", prevMonth + plusMonth)
                var targetYearMonth = "$targetYear$targetMonth"

                val sdf = SimpleDateFormat("yyyyMM")
                val date = sdf.parse(targetYearMonth)
                targetYearMonth = sdf.format(date.time)

                return getLatestDate(targetYearMonth)
            } else {
                val prevMonth = prevDate.substring(4, 6).toInt()
                val prevDay = prevDate.substring(6, 8).toInt()

                val plusMonth = plusDate.substring(0, 2).toInt()
                val plusDay = plusDate.substring(2, 4).toInt()

                val targetYear = prevDate.substring(0, 4)
                val targetMonth = String.format("%02d", prevMonth + plusMonth)
                val targetDay = String.format("%02d", prevDay + plusDay)

                val targetDate = "$targetYear$targetMonth$targetDay"
                val sdf = SimpleDateFormat("yyyyMMdd")
                val parse = sdf.parse(targetDate)
                return sdf.format(parse.time)
            }
        }

        fun getLatestDate(yearMonth: String): String {
            val sdf = SimpleDateFormat("yyyyMM")
            val date = sdf.parse(yearMonth)
            val cal = Calendar.getInstance()
            cal.time = date
            val day = String.format("%02d", cal.getActualMaximum(Calendar.DAY_OF_MONTH))
            return "$yearMonth$day"
        }

        fun compareDate(a: String, b: String, sdf: SimpleDateFormat): Boolean {
            if (a == b) return true
            return sdf.parse(a).before(sdf.parse(b))
        }

        fun getRandomDate(): String {

            val year = (2000..2023).random().toString()
            val month = String.format("%02d", (1..12).random())
            val day = getRandomDay(year, month)

            return "$year$month$day"
        }

        private fun getRandomDay(year: String, month: String): String {
            val sdf = SimpleDateFormat("yyyyMM")
            val date = sdf.parse("$year$month")
            val cal = Calendar.getInstance()
            cal.time = date
            val firstDay = 1
            val lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

            return String.format("%02d", (firstDay..lastDay).random())
        }
    }


}