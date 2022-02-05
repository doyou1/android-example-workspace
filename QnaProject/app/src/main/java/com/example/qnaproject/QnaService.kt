package com.example.qnaproject

import retrofit2.Call
import retrofit2.http.*

public interface QnaService {

    /**
     * Qna화면에 출력할 Qna List Object 리턴
     */
    @Headers("content-type: application/json; charset=utf-8")
    @GET("api/faq/FaqList")
    fun getQnaList(@Query("MEM_ID") MEM_ID: Int, @Query("PAGE") PAGE: Int): Call<QnaResponseModel>

    /**
     * Qna상세화면에 출력할 Qna Detail Object 리턴
     */
    @Headers("content-type: application/json; charset=utf-8")
    @GET("api/faq/FaqInfo")
    fun getQnaDetail(@Query("QNA_ID") QNA_ID: Int): Call<QnaDetailResponseModel>

    /**
     * 작성한 Qna 서버에 등록
     */
    @Headers("content-type: application/json; charset=utf-8")
    @POST("api/faq/RegisterFAQ")
    fun registerNewQna(@Body newQna: NewQna) : Call<NewQnaResponseModel>


}