package com.example.qnaproject.service

import com.example.qnaproject.responseModel.ProductResponseModel
import retrofit2.Call
import retrofit2.http.*


/**
 * 상품 인터페이스 URL 요청 서비스 interface
 */
interface ProductService {

    /**
     * ProductActivity - HomeFragment - RecycierView
     * 상품정보 리스트
     */
    @Headers("content-type: application/json; charset=utf-8")
    @GET("api/goods/GoodList")
    fun getProductList(@Query("MEM_ID") MEM_ID: Int, @Query("ITM_ONLY_VIEW") ITM_ONLY_VIEW: String, @Query("PAGE") PAGE: Int): Call<ProductResponseModel>

    /**
     * ProductActivity - UpdateFragment - Product
     * 상품정보
     */
    @Headers("content-type: application/json; charset=utf-8")
    @GET("api/goods/GetGood")
    fun getProduct(@Query("ITM_ID") ITM_ID: Int): Call<ProductResponseModel>


}