package com.example.pedometeruiappsampling.util

import com.example.pedometeruiappsampling.domain.WeekGoal

const val TEXT_SUN = "SUN"
const val TEXT_MON = "MON"
const val TEXT_TUE = "TUE"
const val TEXT_WED = "WED"
const val TEXT_THU = "THU"
const val TEXT_FRI = "FRI"
const val TEXT_SAT = "SAT"

val DATA_WEEK_GOAL = listOf(
    WeekGoal(true, TEXT_SUN),
    WeekGoal(true, TEXT_MON),
    WeekGoal(false, TEXT_TUE),
    WeekGoal(false, TEXT_WED),
    WeekGoal(false, TEXT_THU),
    WeekGoal(false, TEXT_FRI),
    WeekGoal(false, TEXT_SAT),
)