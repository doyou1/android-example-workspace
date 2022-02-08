package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.Product
import com.example.qnaproject.domain.Qna


data class ProductResponseModel(val code:Int, val message:String, val data: ArrayList<Product>) {

}