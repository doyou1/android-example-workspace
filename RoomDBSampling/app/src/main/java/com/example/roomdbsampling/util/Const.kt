package com.example.roomdbsampling.util

import com.example.roomdbsampling.entity.History

object Const {
    const val DB_NAME = "ACCOUNT_TEST"
    const val TEXT_INIT = "init"

    val INIT_INCOME_ASSETS = arrayOf(
        "현금",
        "카드",
        "은행",
        "기타",
    )
    val INIT_CONSUMPTION_ASSETS = arrayOf(
        "현금",
        "카드",
        "은행",
        "기타"
    )
    val INIT_TRANSFER_ASSETS = arrayOf(
        "현금",
        "신한은행",
        "미즈호",
        "해외송금",
        "기타",
    )

    val INIT_INCOME_CATEGORIES = arrayOf(
        "월급",
        "상여",
        "환급",
        "이자",
        "기타",
    )
    val INIT_CONSUMPTION_CATEGORIES = arrayOf(
        "식비",
        "교통비",
        "공과금",
        "경조사",
        "옷",
        "사치",
        "적금",
        "기타"
    )
    val INIT_TRANSFER_CATEGORIES = arrayOf(
        "부모님",
        "은행간이동",
        "해외송금",
        "기타",
    )

    const val TEXT_INCOME = "INCOME"
    const val TEXT_CONSUMPTION = "CONSUMPTION"
    const val TEXT_TRANSFER = "TRANSFER"
}