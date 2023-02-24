package com.example.pedometeruiappsampling.util

import com.example.pedometeruiappsampling.domain.WeekGoal

const val TEXT_SUN = "SUN"
const val TEXT_MON = "MON"
const val TEXT_TUE = "TUE"
const val TEXT_WED = "WED"
const val TEXT_THU = "THU"
const val TEXT_FRI = "FRI"
const val TEXT_SAT = "SAT"
const val TEXT_TODAY = "Today"
const val TEXT_BAR_CHART = "BarChart"
const val TEXT_GOAL = "goal"
const val TEXT_AVERAGE = "Average"
const val DEFAULT_GOAL = 10000
const val SIZE_GOAL_LINE = 24f
const val SIZE_LINE_WIDTH = 8f
const val SIZE_SPACE = 0.5f
const val TEXT_SIZE_X_AXIS = 12f
const val EXTRA_BOTTOM_OFFSET = 12f
const val SIZE_X_RANGE_MAXIMUM = 6f
const val SIZE_BAR_WIDTH = 0.25f
const val TEXT_SIZE_AVERAGE_LINE = 24f

val DATA_WEEK_GOAL = listOf(
    WeekGoal(true, TEXT_SUN),
    WeekGoal(true, TEXT_MON),
    WeekGoal(false, TEXT_TUE),
    WeekGoal(false, TEXT_WED),
    WeekGoal(false, TEXT_THU),
    WeekGoal(false, TEXT_FRI),
    WeekGoal(false, TEXT_SAT),
)