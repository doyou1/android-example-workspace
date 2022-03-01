package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.Product
import com.example.qnaproject.domain.Qna

/**
 * 상품정보 인터페이스 URL 호출 응답 데이터(상품리스트 데이터)
 */
data class ProductResponseModel(val code:Int, val message:String, val data: ArrayList<Product>) {

}