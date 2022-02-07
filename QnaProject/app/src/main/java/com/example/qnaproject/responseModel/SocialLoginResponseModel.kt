package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.SocialUser

data class SocialLoginResponseModel(val code:Int, val message:String, val data: Array<SocialUser>?) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}