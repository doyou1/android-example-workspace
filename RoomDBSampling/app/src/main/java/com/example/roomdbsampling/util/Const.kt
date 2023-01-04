package com.example.roomdbsampling.util

import com.example.roomdbsampling.entity.History

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
const val TEXT_LAUNCH_DATE = "LAUNCH_DATE"

const val TEXT_NONE = "NONE"
const val TEXT_EVERY_1_MINUTES = "EVERY 1 MINUTES"
const val TEXT_EVERY_3_MINUTES = "EVERY 3 MINUTES"
const val TEXT_EVERY_5_MINUTES = "EVERY 5 MINUTES"
const val TEXT_EVERY_10_MINUTES = "EVERY 10 MINUTES"
const val TEXT_EVERY_15_MINUTES = "EVERY 15 MINUTES"
const val TEXT_EVERY_30_MINUTES = "EVERY 30 MINUTES"
const val TEXT_EVERY_45_MINUTES = "EVERY 45 MINUTES"
const val TEXT_EVERY_1_HOURS = "EVERY 1 HOURS"
const val TEXT_EVERY_2_HOURS = "EVERY 2 HOURS"
const val TEXT_EVERY_6_HOURS = "EVERY 6 HOURS"
const val TEXT_EVERY_12_HOURS = "EVERY 12 HOURS"

const val FLAG_NONE = -1
const val FLAG_EVERY_1_MINUTES = 0
const val FLAG_EVERY_3_MINUTES = 1
const val FLAG_EVERY_5_MINUTES = 2
const val FLAG_EVERY_10_MINUTES = 3
const val FLAG_EVERY_15_MINUTES = 4
const val FLAG_EVERY_30_MINUTES = 5
const val FLAG_EVERY_45_MINUTES = 6
const val FLAG_EVERY_1_HOURS = 7
const val FLAG_EVERY_2_HOURS = 8
const val FLAG_EVERY_6_HOURS = 9
const val FLAG_EVERY_12_HOURS = 10

const val STRING_EVERY_1_MINUTES = "0001"
const val STRING_EVERY_3_MINUTES = "0003"
const val STRING_EVERY_5_MINUTES = "0005"
const val STRING_EVERY_10_MINUTES = "0010"
const val STRING_EVERY_15_MINUTES = "0015"
const val STRING_EVERY_30_MINUTES = "0030"
const val STRING_EVERY_45_MINUTES = "0045"
const val STRING_EVERY_1_HOURS = "0100"
const val STRING_EVERY_2_HOURS = "0200"
const val STRING_EVERY_6_HOURS = "0600"
const val STRING_EVERY_12_HOURS = "1200"


val REPEAT_ITEM_LIST = arrayOf(
    TEXT_NONE,
    TEXT_EVERY_1_MINUTES,
    TEXT_EVERY_3_MINUTES,
    TEXT_EVERY_5_MINUTES,
    TEXT_EVERY_10_MINUTES,
    TEXT_EVERY_15_MINUTES,
    TEXT_EVERY_30_MINUTES,
    TEXT_EVERY_45_MINUTES,
    TEXT_EVERY_1_HOURS,
    TEXT_EVERY_2_HOURS,
    TEXT_EVERY_6_HOURS,
    TEXT_EVERY_12_HOURS
)

