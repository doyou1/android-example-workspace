package com.example.qnaproject.domain

/**
 * QnaDetail(상세정보) 모델
 */
data class QnaDetail(val QNA_TITLE: String, val QNA_CONTENT: String, val QNA_ANSWER:String?, val QNA_CON_DT:String, val QNA_ANN_DT:String?){
}
