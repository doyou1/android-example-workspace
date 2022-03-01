package com.example.qnaproject.domain

/**
 * QnaRegister (문의 등록 모델)
 */
data class NewQna(val MEM_ID: Int, val QNA_TITLE: String, val QNA_CONTENT: String) {
}