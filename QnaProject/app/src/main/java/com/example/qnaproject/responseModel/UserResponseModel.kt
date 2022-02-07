package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.User

/**
 * 회원가입(UserJoin) 인터페이스 URL 전송 데이터 모델
 *
 * 해당 데이터 클래스는 회원가입 URL 응답 데이터 모델로 활용
 * MEM_ID를 받기 위해 활용
 */
data class UserResponseModel(val code:Int, val message:String, val data: Array<User>?) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}