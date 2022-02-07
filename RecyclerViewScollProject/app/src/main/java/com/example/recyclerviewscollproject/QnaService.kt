package com.example.recyclerviewscollproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface QnaService {

    @Headers("content-type: application/json; charset=utf-8")
    @GET("api/faq/FaqList")
    fun getQnaList(@Query("MEM_ID") MEM_ID: Int, @Query("PAGE") PAGE: Int) : Call<QnaResponseModel>

}