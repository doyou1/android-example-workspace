package com.example.qnaproject.domain

import com.example.qnaproject.fragment.EstimationListResponseModel
import com.example.qnaproject.fragment.PopupListResponseModel
import retrofit2.Call
import retrofit2.http.*

interface NetworkDataService {

    @Headers("content-type: application/json; charset=utf-8")
    @GET("api/estimation/EstiListPaging")
    fun getEstimationList(
        @Query("CATEGORY_CD") categoryCd: String,
        @Query("MEM_ID") memId: Int,
        @Query("PAGE") page: Int,
    ): Call<EstimationListResponseModel>

    @GET("api/home/PopupList")
    fun getPopupList(): Call<PopupListResponseModel>
}