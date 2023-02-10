package com.example.pedometersampling.room

import android.content.Context
import androidx.room.Room
import com.example.pedometersampling.AppDataBase
import com.example.pedometersampling.TEXT_PEDOMETER
import com.example.pedometersampling.util.DBUtil
import com.example.pedometersampling.util.Util

class DBHelper {
    companion object {
        fun process(context: Context, steps: Int) {
            val db = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, TEXT_PEDOMETER).build().pedometerDao()
            val currentDate = Util.getCurrentDate()

            // 오늘에 대한 레코드 있는지 확인
            val result = db.existByDate(Util.getCurrentDate())

            // 레코드 없으면
            if(result == 0) {
                // insert
                val pedometer = Pedometer(
                    0,
                    currentDate,
                    steps,
                    "{\"steps\": []}"
                )
                db.insert(pedometer)
            }
            // 레코드 있으면
            else {
                val item = db.getByDate(Util.getCurrentDate())

                val prevStepSum = DBUtil.getPrevStepSum(item.steps)
                val currentSteps = steps - (item.initSteps + prevStepSum)
                val newSteps = DBUtil.addSteps(item.steps, Util.getCurrentTime(), currentSteps)
                item.steps = newSteps
                db.update(item)
            }
        }
    }
}