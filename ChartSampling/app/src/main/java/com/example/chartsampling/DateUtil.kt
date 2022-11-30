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

                // 하루 전
                date.add(Calendar.DATE, -1)
            }

            // 오늘부터 쌓았기때문에 reverse가 필요, immutable한 array로 변경
            return list.asReversed().toTypedArray()
        }
    }
}

data class StepModel (val date: String, val step: Int)