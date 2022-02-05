package com.example.qnaproject

class QnaDetailResponseModel(val code:Int, val message:String, val data: ArrayList<QnaDetail>) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}