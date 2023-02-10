package com.example.pedometersampling.util

import android.util.Log
import com.example.pedometersampling.room.dto.Steps
import com.example.pedometersampling.room.dto.StepsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.invoke.MethodType

class DBUtil {
    companion object {
        private val TAG = this::class.java.simpleName

        fun getPrevStepSum(json: String): Int {
            // steps: [{'0010': 23}, {'0020': 30}, {'0030': 15}]
            Log.d(TAG, "steps: $json")
            var result = 0
            val steps = Util.fromStepsJson(json)
            for (step in steps) {
                result += step.steps
            }
            return result
        }


        fun addSteps(json: String, currentHour: String, currentSteps: Int): String {
            val result = arrayListOf<StepsItem>()
            val steps = Util.fromStepsJson(json)
            val filter = steps.filter { step -> step.hour == currentHour }
            // 오늘 해당 시가 있을 경우
            if (filter.isNotEmpty()) {
                for (step in steps) {
                    // 해당 시에 step 추가하는 방식
                    if (step.hour == currentHour) {
                        val newSteps = step.steps + currentSteps
                        val new = StepsItem(step.hour, newSteps)
                        result.add(new)
                    } else {
                        result.add(step)
                    }
                }
            }
            // 오늘 해당 시가 없을 경우
            else {
                result.addAll(steps)
                val current = StepsItem(currentHour, currentSteps)
                result.add(current)
            }
            return Gson().toJson(result)
        }
    }
}