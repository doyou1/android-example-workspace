package com.example.qnaproject

class NewQnaResponseModel(val code:Int, val message:String, val data: NewQna) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}