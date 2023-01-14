package com.example.roomdbimagesampling

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageDao {

    @Query("SELECT * FROM IMAGE")
    fun getAll(): List<Image>

    @Query("SELECT * FROM IMAGE WHERE ID = :id")
    fun getById(id: Int): List<Image>

    @Insert
    fun insert(item: Image)

    @Insert
    fun insertAll(list: List<Image>)

}