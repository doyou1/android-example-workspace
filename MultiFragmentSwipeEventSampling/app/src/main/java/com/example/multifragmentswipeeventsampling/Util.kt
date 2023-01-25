package com.example.multifragmentswipeeventsampling

import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object {
        fun getFullDateText(date: String): String {
            val sdf = SimpleDateFormat("yyyyMMdd")
            val date = sdf.parse(date)
            val cal = Calendar.getInstance()
            cal.time = date

            val fsdf = SimpleDateFormat("yyyyMMdd(EEE)", Locale.ENGLISH)
            return fsdf.format(cal.time)
        }
    }
}