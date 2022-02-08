package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.Product
import com.example.qnaproject.domain.Qna


class ProductResponseModel(val code:Int, val message:String, val data: ArrayList<Product>) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}