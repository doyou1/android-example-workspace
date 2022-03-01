package com.example.qnaproject.domain

data class Estimation(
    val CATEGORY_CD: String,
    val ID: Long,
    val REQ_NAME: String,
    val REQ_DT: String,
    val AREA_CODE: String,
    val REPAIR_POST_NM: String?,
    val CARRE_BUY_TYPE: String,
    val AREA_NM: String,
    val CLOSE_YN: String?,
    val READ_YN: String?,
    val SEND_EST_YN: String?,
    val MEM_NICK: String?,
    val REV_YN: String
)