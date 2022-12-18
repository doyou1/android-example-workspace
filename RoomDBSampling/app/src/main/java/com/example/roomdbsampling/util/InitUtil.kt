package com.example.roomdbsampling.util

import java.util.*
import kotlin.math.roundToInt

class InitUtil {

    companion object {
        fun getRandomDate(): String {
            val cal = Calendar.getInstance()

            val year = 2022
            cal.set(Calendar.YEAR, year)
            val dayOfYear = randBetween(1, cal.getActualMaximum(Calendar.DAY_OF_YEAR))
            cal.set(Calendar.DAY_OF_YEAR, dayOfYear)

            return "${cal.get(Calendar.YEAR)}${
                String.format(
                    "%02d",
                    cal.get(Calendar.MONTH) + 1
                )
            }${String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))}"
        }

        fun getRandomAsset(type: Int): String {

            return when (type) {
                // income
                0 -> {
                    Const.INIT_INCOME_ASSETS[randBetween(0, Const.INIT_INCOME_ASSETS.size - 1)]
                }
                // consumption
                1 -> {
                    Const.INIT_CONSUMPTION_ASSETS[randBetween(0, Const.INIT_CONSUMPTION_ASSETS.size - 1)]
                }
                // transfer
                2 -> {
                    Const.INIT_TRANSFER_ASSETS[randBetween(0, Const.INIT_TRANSFER_ASSETS.size - 1)]
                }
                else -> throw NotImplementedError()
            }
        }

        fun getRandomCategory(type: Int): String {
            return when (type) {
                // income
                0 -> {
                    Const.INIT_INCOME_CATEGORIES[randBetween(0, Const.INIT_INCOME_CATEGORIES.size - 1)]
                }
                // consumption
                1 -> {
                    Const.INIT_CONSUMPTION_CATEGORIES[randBetween(0, Const.INIT_CONSUMPTION_CATEGORIES.size - 1)]
                }
                // transfer
                2 -> {
                    Const.INIT_TRANSFER_CATEGORIES[randBetween(0, Const.INIT_TRANSFER_CATEGORIES.size - 1)]
                }
                else -> throw NotImplementedError()
            }
        }

        fun randBetween(start: Int, end: Int): Int {
            return start + (Math.random() * (end - start)).roundToInt()
        }
    }
}