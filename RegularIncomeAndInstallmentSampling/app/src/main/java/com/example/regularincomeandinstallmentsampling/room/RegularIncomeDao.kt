package com.example.regularincomeandinstallmentsampling.room

import androidx.room.*
import com.example.regularincomeandinstallmentsampling.regularincome.RegularIncome

@Dao
interface RegularIncomeDao {

    @Query("SELECT * FROM RegularIncome")
    fun getAll() : List<RegularIncome>

    @Insert
    fun insert(item: RegularIncome)

    @Insert
    fun insertAll(items: List<RegularIncome>)

    @Update
    fun update(item: RegularIncome)

    @Delete
    fun delete(item: RegularIncome)
}