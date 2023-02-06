package com.example.pedometerclone.util

import java.util.*

const val TEXT_PEDOMETER = "pedometer"
const val TEXT_PAUSECOUNT = "pauseCount"
const val TEXT_NOTIFICATION = "notification"
const val TEXT_GOAL = "goal"

const val DEFAULT_GOAL = 10000;

val DEFAULT_STEP_SIZE = if (Locale.getDefault() == Locale.US) 2.5f else 75f
val DEFAULT_STEP_UNIT = if (Locale.getDefault() == Locale.US) "ft" else "cm"
