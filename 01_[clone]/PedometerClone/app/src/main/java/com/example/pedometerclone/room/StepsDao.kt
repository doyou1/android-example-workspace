package com.example.pedometerclone.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StepsDao {

    @Query("SELECT steps FROM Steps WHERE date=:date")
    fun getSteps(date: Long): Int

    @Insert
    fun insertNewDay(steps: Steps)

    @Query("UPDATE steps SET steps = :steps WHERE date = -1")
    fun saveCurrentSteps(steps: Int)

    @Query("SELECT steps FROM Steps WHERE date=-1")
    fun getCurrentSteps(): Int

    @Query("UPDATE steps SET steps = steps + :steps WHERE date = (SELECT MAX(date) FROM steps)")
    fun addToLastEntry(steps: Int)

    @Query("SELECT SUM(steps) from steps where steps > 0 and date > 0 and date < :date")
    fun getTotalWithoutToday(date: Long) : Int

    @Query("SELECT count(*) FROM steps WHERE steps > 0 and date < :date and date > 0")
    fun getDaysWithoutToday(date: Long) : Int

}