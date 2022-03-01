package com.example.qnaproject.service

import com.example.qnaproject.domain.User
import com.example.qnaproject.responseModel.SocialLoginResponseModel
import com.example.qnaproject.responseModel.UserResponseModel
import retrofit2.Call
import retrofit2.http.*

/**
 * 유저 인터페이스 URL 요청 서비스 interface
 */
interface UserService {

    /**
     * 작성한 Qna 서버에 등록
     */
    @Headers("content-type: application/json; charset=utf-8")
    @POST("api/user/join/UserJoin")
    fun joinUser(@Body user: User) : Call<UserResponseModel>

    /**
     * 작성한 Qna 서버에 등록
     */
    @Headers("content-type: application/json; charset=utf-8")
    @GET("api/user/login/Login")
    fun socialLogin(@Query("MEM_SNS_TYPE") MEM_SNS_TYPE: String, @Query("MEM_SNS_ID") MEM_SNS_ID: String): Call<SocialLoginResponseModel>

}