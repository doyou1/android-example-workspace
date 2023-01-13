package com.example.roomdbsampling.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: Int,
    val regular: Int,
    val installment: Int,
    val date: String,
    val assetId: Int,
    val assetName: String,
    val categoryId: Int,
    val categoryName: String,
    val amount: Int,
    val memo: String?
)
