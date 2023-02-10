package com.example.pedometersampling.util

import android.content.Context
import com.example.pedometersampling.TEXT_COUNT
import com.example.pedometersampling.TEXT_PEDOMETER
import com.example.pedometersampling.TEXT_SERVICE
import com.example.pedometersampling.TEXT_STEPS
import com.example.pedometersampling.room.dto.StepsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object {

        fun getDate(): String {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            return sdf.format(cal.time)
        }

        fun getTime(): String {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("HH:mm:ss")
            return sdf.format(cal.time)
        }

        fun getCurrentDate(): Long {
            val c = Calendar.getInstance()
            c.timeInMillis = System.currentTimeMillis();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.timeInMillis;
        }

        fun getCurrentHour(): String {
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("HH")
            return sdf.format(c.time)
        }

        fun fromStepsJson(string: String): List<StepsItem> {
            val type = object : TypeToken<List<StepsItem>>() {}.type
            return try {
                Gson().fromJson(string, type)
            } catch (e: Exception) {
                listOf()
            }
        }

        fun getCount(context: Context): String {
            val pref = context.getSharedPreferences(TEXT_COUNT, Context.MODE_PRIVATE)
            val count = pref.getInt(TEXT_COUNT, 0)
            pref.edit().putInt(TEXT_COUNT, count + 1).apply()
            return "$count"
        }

        fun convertDate(time: Long): String {
            val cal = Calendar.getInstance()
            cal.timeInMillis = time
            val sdf = SimpleDateFormat("yyyyMMdd")
            return sdf.format(cal.time)
        }

        fun getSteps(context: Context): String {
            val pref = context.getSharedPreferences(TEXT_PEDOMETER, Context.MODE_PRIVATE)
            return "${pref.getInt(TEXT_STEPS, 0)}"
        }

        fun getServiceSteps(context: Context): String {
            val pref = context.getSharedPreferences(TEXT_PEDOMETER, Context.MODE_PRIVATE)
            return "${pref.getInt(TEXT_SERVICE, 0)}"
        }


    }
}