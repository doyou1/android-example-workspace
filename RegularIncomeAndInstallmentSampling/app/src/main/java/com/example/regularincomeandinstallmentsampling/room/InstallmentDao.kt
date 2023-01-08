package com.example.regularincomeandinstallmentsampling.room

import androidx.room.*
import com.example.regularincomeandinstallmentsampling.installment.Installment

@Dao
interface InstallmentDao {

    @Query("SELECT * FROM Installment")
    fun getAll(): List<Installment>

    @Insert
    fun insert(item: Installment)

    @Insert
    fun insertAll(items: List<Installment>)

    @Update
    fun update(item: Installment)

    @Delete
    fun delete(item: Installment)
}