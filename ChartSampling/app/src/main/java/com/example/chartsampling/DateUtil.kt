package com.example.chartsampling

import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random.Default.nextInt

class DateUtil {
    companion object {
        fun getStepModel(itemSize: Int): Array<StepModel> {
            val date = Calendar.getInstance()
            val formatter = SimpleDateFormat("yyyyMMdd")

            val list = arrayListOf<StepModel>()

            for (i in 0 until itemSize) {
                val strDate = formatter.format(date.time).toString()
                val step = nextInt(0, 10000)
                val stepModel = StepModel(strDate, step)
                list.add(stepModel)

                // prev day
                date.add(Calendar.DATE, -1)
            }

            // reverse, because add today to prev day
            return list.asReversed().toTypedArray()
        }
    }
}

data class StepModel(val date: String, val step: Int)