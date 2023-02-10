package com.example.pedometersampling.room

import android.content.Context
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.pedometersampling.AppDataBase
import com.example.pedometersampling.TEXT_PEDOMETER
import com.example.pedometersampling.room.dto.Steps
import com.example.pedometersampling.util.DBUtil
import com.example.pedometersampling.util.Util
import com.google.gson.Gson
import kotlinx.coroutines.*

class DBHelper {
    companion object {

        private val TAG = this::class.java.simpleName

        fun process(context: Context, steps: Int) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                TEXT_PEDOMETER
            ).build().pedometerDao()

            GlobalScope.launch(Dispatchers.IO) {
                // 오늘에 대한 레코드 있는지 확인
                val result = db.existByDate(Util.getCurrentDate())
                // 레코드 없으면
                if (result == 0) {
                    // insert
                    val pedometer = Pedometer(
                        0,
                        Util.getCurrentDate(),
                        steps,
                        Gson().toJson(Steps(listOf()))
                    )
                    db.insert(pedometer)
                }
                // 레코드 있으면
                else {
                    val item = db.getByDate(Util.getCurrentDate())
                    Log.d(TAG, "steps: ${item.steps}")
                    val prevStepSum = DBUtil.getPrevStepSum(item.steps)
                    val currentSteps = steps - (item.initSteps + prevStepSum)
                    val newSteps = DBUtil.addSteps(item.steps, Util.getCurrentHour(), currentSteps)
                    item.steps = newSteps
                    db.update(item)
                }
            }
        }

        suspend fun getCurrent(context: Context): Pedometer? = withContext(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                TEXT_PEDOMETER
            ).build().pedometerDao()
            return@withContext db.getByDate(Util.getCurrentDate())
        }
    }
}