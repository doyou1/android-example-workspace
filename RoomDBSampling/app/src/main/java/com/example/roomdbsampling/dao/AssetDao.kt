package com.example.roomdbsampling.dao

import androidx.room.*
import com.example.roomdbsampling.dto.Summary
import com.example.roomdbsampling.entity.Asset
import com.example.roomdbsampling.entity.Category
import com.example.roomdbsampling.entity.History

@Dao
interface AssetDao {

    @Query("SELECT count(*) FROM Asset")
    fun getSize(): Int

    @Query("SELECT * FROM Asset")
    fun getAll(): List<Asset>

    @Query("SELECT * FROM Asset WHERE id=:id")
    fun getByid(id: Int): Asset

    @Query("SELECT * FROM Asset WHERE type=:type")
    fun getByType(type: Int): List<Asset>

    @Query("SELECT name FROM Asset WHERE id=:id")
    fun getNameByid(id: Int): String

    @Insert
    fun insert(asset: Asset)

    @Insert
    fun insertAll(assets: List<Asset>)

    @Update
    fun update(history: History)

    @Delete
    fun delete(history: History)


}