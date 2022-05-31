package com.example.ryougaesampling.retrofit

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RougaeService {
    @GET("v1/forex/recent")
    fun getResult2(
        @Query("codes")
        codes:String
    )
            : Call<JsonArray>
}