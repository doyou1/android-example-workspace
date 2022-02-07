package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.NewQna

class NewQnaResponseModel(val code:Int, val message:String, val data: ArrayList<NewQna>) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}