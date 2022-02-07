package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.SocialUser

/**
 * 로그인(회원조회) 인터페이스 URL 응답 데이터 모델
 */
data class SocialLoginResponseModel(val code:Int, val message:String, val data: Array<SocialUser>?) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}