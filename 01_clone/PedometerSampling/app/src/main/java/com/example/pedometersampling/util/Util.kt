package com.example.pedometersampling.util

import com.example.pedometersampling.room.Pedometer
import com.example.pedometersampling.room.dto.StepsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

        fun fillStepsList(list: List<StepsItem>): List<StepsItem> {
            val new = arrayListOf<StepsItem>()
            for (h in 1..24) {
                val hour = String.format("%02d", h)
                val filter = list.filter { item -> item.hour == hour }
                if (filter.isEmpty()) {
                    new.add(StepsItem(hour, 0))
                } else {
                    new.add(filter[0])
                }
            }
            return new.toList()
        }

        fun convertDate(time: Long): String {
            val cal = Calendar.getInstance()
            cal.timeInMillis = time
            val sdf = SimpleDateFormat("yyyyMMdd")
            return sdf.format(cal.time)
        }

        fun computeSteps(item: Pedometer): Int {
            var sum = 0
            val steps = fromStepsJson(item.steps)
            for (step in steps) {
                sum += step.steps
            }
            return sum
        }

        fun stepsToString(item: Pedometer): String {
            var result = "date: ${convertDate(item.date)} \n"
            result += "initSteps: ${item.initSteps} \n"
            val steps = fromStepsJson(item.steps)
            for (step in steps) {
                result += "${step.hour} : ${step.steps} \n"
            }
            return result
        }
    }
}