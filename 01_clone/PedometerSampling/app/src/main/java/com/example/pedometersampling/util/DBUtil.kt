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
            val type = object : TypeToken<List<StepsItem>>() {}.type
            val steps = Gson().fromJson<List<StepsItem>>(json, type)
            var result = 0
            for (step in steps) {
                result += step.steps
            }
            return result
        }


        fun addSteps(json: String, currentTime: String, currentSteps: Int): String {
            val type = object : TypeToken<List<StepsItem>>() {}.type
            val steps = Gson().fromJson<List<StepsItem>>(json, type)
            val result = arrayListOf<StepsItem>()
            result.addAll(steps)
            val current = StepsItem(currentTime, currentSteps)
            result.add(current)
            return Gson().toJson(result)
        }
    }
}