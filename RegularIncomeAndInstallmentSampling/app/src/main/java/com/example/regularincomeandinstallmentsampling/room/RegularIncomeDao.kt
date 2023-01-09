package com.example.regularincomeandinstallmentsampling.room

import androidx.room.*
import com.example.regularincomeandinstallmentsampling.regularincome.RegularIncome

@Dao
interface RegularIncomeDao {

    @Query("SELECT * FROM RegularIncome")
    fun getAll() : List<RegularIncome>

//    val date: String,
    @Query("SELECT * FROM RegularIncome WHERE period != -1 and date <= :today and isNewFlag == 1")
    fun getLatestItems(today: String) : List<RegularIncome>

    @Insert
    fun insert(item: RegularIncome)

    @Insert
    fun insertAll(items: List<RegularIncome>)

    @Update
    fun update(item: RegularIncome)

    @Update
    fun updateAll(items: List<RegularIncome>)

    @Delete
    fun delete(item: RegularIncome)
}