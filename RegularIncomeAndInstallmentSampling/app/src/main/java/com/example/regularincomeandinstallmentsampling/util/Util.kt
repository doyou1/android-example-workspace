package com.example.regularincomeandinstallmentsampling.util

class Util {

    companion object {
        fun getPeriod(flag: Int) : String {
            return when(flag) {
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

    }
}