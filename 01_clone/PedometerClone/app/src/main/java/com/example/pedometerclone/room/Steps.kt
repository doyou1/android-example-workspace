package com.example.pedometerclone.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Steps(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: Long, val steps: Int)