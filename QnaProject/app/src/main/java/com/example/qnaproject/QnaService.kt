package com.example.qnaproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

public interface QnaService {

    /**
     * Qna화면에 출력할 Qna List Object 리턴
     */
    @Headers("content-type: application/json; charset=utf-8")
    @GET("api/faq/FaqList")
    fun getQnaList(@Query("MEM_ID") MEM_ID: Int, @Query("PAGE") PAGE: Int): Call<ResponseModel>

    /**
     * Qna상세화면에 출력할 Qna Detail Object 리턴
     */
    @Headers("content-type: application/json; charset=utf-8")
    @GET("api/faq/FaqInfo")
    fun getQnaDetail(@Query("QNA_ID") QNA_ID: Int): Call<ResponseModel>


}