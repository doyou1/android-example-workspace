package com.example.roomdbimagesampling

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface ImageService {

    @POST("/api/get")
    fun get(
        @Body names: List<String>
    ): Call<HashMap<String, String>>

    @Multipart
    @POST("/api/upload")
    fun upload(
        @Part images: List<MultipartBody.Part>
    ): Call<Int>


}