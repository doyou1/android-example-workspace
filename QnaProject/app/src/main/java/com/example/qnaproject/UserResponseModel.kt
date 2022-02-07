package com.example.qnaproject

data class UserResponseModel(val code:Int, val message:String, val data: Array<User>?) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}