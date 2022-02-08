package com.example.qnaproject.service

import com.example.qnaproject.responseModel.ProductResponseModel
import retrofit2.Call
import retrofit2.http.*


interface ProductService {

    /**
     * Qna화면에 출력할 Qna List Object 리턴
     */
    @Headers("content-type: application/json; charset=utf-8")
    @GET("api/goods/GoodList")
    fun getProductList(@Query("MEM_ID") MEM_ID: Int, @Query("ITM_ONLY_VIEW") ITM_ONLY_VIEW: String, @Query("PAGE") PAGE: Int): Call<ProductResponseModel>

}