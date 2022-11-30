package com.example.chartsampling

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val date = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyyMMdd")

        val list = arrayListOf<StepModel>()

        for (i in 0..5) {
            val strDate = formatter.format(date.time).toString()
            val step = Random.nextInt(0, 10000)
            val stepModel = StepModel(strDate, step)
            list.add(stepModel)

            // 하루 전
            date.add(Calendar.DATE, -1)
        }

        // 오늘부터 쌓았기때문에 reverse가 필요, immutable한 array로 변경
        println(list.asReversed().toTypedArray()[0])

        assertEquals(4, 2 + 2)
    }
}