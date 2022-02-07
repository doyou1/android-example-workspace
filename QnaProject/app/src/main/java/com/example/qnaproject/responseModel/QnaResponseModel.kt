package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.Qna

/**
 * 문의리스트 URL 호출 이후 응답 데이터 모델
 */
class QnaResponseModel(val code:Int, val message:String, val data: ArrayList<Qna>) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}