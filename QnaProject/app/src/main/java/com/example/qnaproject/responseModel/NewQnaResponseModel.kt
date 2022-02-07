package com.example.qnaproject.responseModel

import com.example.qnaproject.domain.NewQna

/**
 * 문의 등록 인터페이스 URL 호출 이후 응답 데이터 모델
 */
class NewQnaResponseModel(val code:Int, val message:String, val data: ArrayList<NewQna>) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}