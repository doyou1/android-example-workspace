package com.example.roomsampling

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll() : List<User>

    @Query("SELECT * FROM user WHERE uid=:uid")
    fun getByUid(uid: Int) : User

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)
}