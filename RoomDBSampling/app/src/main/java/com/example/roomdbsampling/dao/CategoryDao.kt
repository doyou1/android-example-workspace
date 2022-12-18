package com.example.roomdbsampling.dao

import androidx.room.*
import com.example.roomdbsampling.dto.Summary
import com.example.roomdbsampling.entity.Category
import com.example.roomdbsampling.entity.History

@Dao
interface CategoryDao {

    @Query("SELECT count(*) FROM Category")
    fun getSize(): Int

    @Query("SELECT * FROM Category")
    fun getAll(): List<Category>

    @Query("SELECT * FROM Category WHERE id=:id")
    fun getByid(id: Int): Category

    @Query("SELECT * FROM Category WHERE type=:type")
    fun getByType(type: Int): List<Category>

    @Query("SELECT name FROM Category WHERE id=:id")
    fun getNameByid(id: Int): String

    @Insert
    fun insert(category: Category)

    @Insert
    fun insertAll(categories: List<Category>)

    @Update
    fun update(history: History)

    @Delete
    fun delete(history: History)



}