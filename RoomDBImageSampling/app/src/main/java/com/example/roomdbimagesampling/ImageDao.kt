package com.example.roomdbimagesampling

import androidx.room.*

@Dao
interface ImageDao {


    @Query("SELECT * FROM Image")
    fun getAll(): List<Image>

    @Insert
    fun insert(image: Image) : Long

    @Insert
    fun insertAll(images: List<Image>)

    @Update
    fun update(image: Image)

    @Delete
    fun delete(image: Image)
}