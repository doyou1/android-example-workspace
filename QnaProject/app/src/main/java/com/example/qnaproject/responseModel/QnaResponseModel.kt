package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.Qna

class QnaResponseModel(val code:Int, val message:String, val data: ArrayList<Qna>) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}