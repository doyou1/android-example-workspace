package com.example.regularincomeandinstallmentsampling.installment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Installment(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val period: Int,
    val date: String,
    val name: String,
    val amount: Int,
    val isNewFlag: Boolean,
    val remainCount: Int
)
