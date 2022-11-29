package com.example.roomsampling

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserViewModel(val uid: String, val id: String, val pwd: String, val name: String)