package com.example.roomdbstringjsonsampling

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemDao {

    @Insert
    fun insert(item: Item)

    @Query("SELECT * FROM Item WHERE id = :id")
    fun getById(id: Long): Item

    @Query("SELECT id FROM item")
    fun getIds() : List<Long>

    @Query("SELECT * FROM Item")
    fun getAll() : List<Item>
}