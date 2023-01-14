package com.example.roomdbimagesampling

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File

data class ImageDto(
    val name: String,
    val value: ByteArray
)