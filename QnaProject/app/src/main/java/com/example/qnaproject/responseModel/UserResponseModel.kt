package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.User

data class UserResponseModel(val code:Int, val message:String, val data: Array<User>?) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}