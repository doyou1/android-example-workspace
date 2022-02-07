package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.QnaDetail

class QnaDetailResponseModel(val code:Int, val message:String, val data: ArrayList<QnaDetail>) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}