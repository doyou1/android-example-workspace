package com.example.regularincomeandinstallmentsampling.regularincome

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RegularIncome(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val period: Int, val date: String, val name: String, val amount: Int, var isNewFlag: Boolean
)
