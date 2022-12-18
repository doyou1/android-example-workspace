package com.example.roomdbsampling.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: Int,
    val date: String,
    val asset: String,
    val category: String,
    val amount: Int,
    val memo: String?
)
