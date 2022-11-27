package com.example.roomsampling

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey val uid: Int, val id: String, val pwd: String, val name: String)
