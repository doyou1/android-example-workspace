package com.example.ryougaesampling

import android.util.Log
import com.example.ryougaesampling.retrofit.RougaeResponseModel
import com.example.ryougaesampling.retrofit.RougaeService
import com.google.gson.Gson
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RougaeUtil {

    private val TAG = this::class.java.simpleName.toString()
    //    private val API_KEY = resources.getString(R.string.api_key)
    private val baseUrl = "https://quotation-api-cdn.dunamu.com/"
    private val codes = "FRX.KRWJPY"

    fun getRougae(): String {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rougaeService = retrofit.create(RougaeService::class.java)
        val call = rougaeService.getResult2(codes)

        val result = Gson().fromJson(call.execute().body()?.get(0), RougaeResponseModel::class.java)
        Log.e(TAG, "result: $result")

        return "${result.date} ${result.time} \n ₩1000 = ¥${result.basePrice}"
    }
}