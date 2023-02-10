package com.example.pedometersampling.room.dto

data class Steps(val items: List<StepsItem>?)
data class StepsItem(val time: String, val steps: Int)