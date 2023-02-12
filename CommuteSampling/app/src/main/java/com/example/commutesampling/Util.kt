package com.example.commutesampling

import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object {
        fun getCurrentTime(): String {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
            return sdf.format(cal.time)
        }

        fun getCurrentDate(): String {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            return sdf.format(cal.time)
        }
    }
}