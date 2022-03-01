package com.example.recyclerviewscollproject

class QnaResponseModel(val code:Int, val message:String, val data: MutableList<Qna>) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}