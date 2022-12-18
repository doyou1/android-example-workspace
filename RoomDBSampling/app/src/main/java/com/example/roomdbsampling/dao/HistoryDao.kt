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

    @Query("SELECT type, sum(amount) as result FROM HISTORY WHERE date like :date || '%' group by type")
    fun getSummaryByDate(date: String): List<Summary>

    @Insert
    fun insert(history: History)

    @Insert
    fun insertAll(histories: List<History>)

    @Update
    fun update(history: History)

    @Delete
    fun delete(history: History)



}