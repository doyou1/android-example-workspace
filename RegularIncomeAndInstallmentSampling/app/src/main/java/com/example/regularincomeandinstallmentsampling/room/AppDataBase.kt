package com.example.regularincomeandinstallmentsampling.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.regularincomeandinstallmentsampling.installment.Installment
import com.example.regularincomeandinstallmentsampling.regularincome.RegularIncome

@Database(entities = [RegularIncome::class, Installment::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun regularIncomeDao(): RegularIncomeDao
    abstract fun installmentDao(): InstallmentDao
}
