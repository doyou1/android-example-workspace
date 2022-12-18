package com.example.roomdbsampling.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asset(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: Int,
    val groupId: Int,
    val name: String,
    val amount: Int,
    val memo: String?
)
