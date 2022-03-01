package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.QnaDetail

/**
 * 문의 상세페이지 URL 호출 이후 응답 데이터 모델
 */
data class QnaDetailResponseModel(val code:Int, val message:String, val data: ArrayList<QnaDetail>) {

}