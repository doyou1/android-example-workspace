package com.example.roomdbsampling.dao

import androidx.room.*
import com.example.roomdbsampling.dto.Summary
import com.example.roomdbsampling.entity.History

@Dao
interface HistoryDao {

    @Query("SELECT * FROM History")
    fun getAll(): List<History>

    @Query("SELECT * FROM History WHERE id=:id")
    fun getByid(id: Int): History

    @Query("SELECT * FROM History WHERE date like :date || '%'")
    fun getByDate(date: String?): List<History>

    @Query("SELECT * FROM History WHERE substr(date, 1, 8) <= substr(:date, 1, 8) and repeat != -1 and type = 0")
    fun getReservedRegularIncomeByDate(date: String): List<History>

    @Query("SELECT * FROM History WHERE substr(:today, 1, 8) <= substr(date, 1, 8) and substr(date, 1, 8) <= substr(:date, 1, 8) and repeat != -1 and type = 0")
    fun getReservedRegularIncomeByPeriod(date: String, today: String): List<History>

    @Query("SELECT type, sum(amount) as result FROM HISTORY WHERE date like :date || '%' group by type")
    fun getSummaryByDate(date: String): List<Summary>

    @Query("SELECT id FROM HISTORY WHERE type = :type and repeat = :repeat and date = :date")
    fun isExistByItem(type: Int, repeat: Int, date: String): Int

    @Insert
    fun insert(history: History)

    @Insert
    fun insertAll(histories: List<History>)

    @Update
    fun update(history: History)

    @Delete
    fun delete(history: History)


}