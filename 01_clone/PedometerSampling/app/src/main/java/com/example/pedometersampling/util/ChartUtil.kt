package com.example.pedometersampling.util

import android.graphics.Color
import com.example.pedometersampling.room.Pedometer
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class ChartUtil {
    companion object {
        fun generatePieData(item: Pedometer?): PieData {
            val entries = arrayListOf<PieEntry>()

            val steps = if (item == null) 0 else Util.computeSteps(item)
            entries.add(PieEntry(steps.toFloat(), "current steps"))
            val goal = if (steps > GOAL_STEPS) 0 else GOAL_STEPS - steps
            entries.add(PieEntry(goal.toFloat(), "steps to reach for goal"))

            val datasets = PieDataSet(entries, "steps")
            datasets.colors = ColorTemplate.VORDIPLOM_COLORS.toList()
            datasets.sliceSpace = 2f
            datasets.valueTextColor = Color.WHITE
            datasets.valueTextSize = 24f
            return PieData(datasets)
        }
    }
}