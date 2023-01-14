package com.example.roomdbimagesampling

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import java.io.File

interface ImageService {

    @POST("/api/get")
    fun get(): Call<List<Image>>

    @Multipart
    @POST("/api/upload")
    fun upload(
        @Part("id") id: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): Call<Int>


}