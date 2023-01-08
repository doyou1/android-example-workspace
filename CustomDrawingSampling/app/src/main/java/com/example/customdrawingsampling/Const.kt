package com.example.customdrawingsampling

import android.graphics.Color

const val TEXT_COUNT = "COUNT"
const val TEXT_USER = "USER"
const val TEXT_GOAL = "GOAL"
const val MAX_SPAN_COUNT = 5
const val MIN_LADDER_SIZE = 2
const val MAX_LADDER_SIZE = 10
const val FLAG_GOAL = 1
const val FLAG_USER = 0
val COLOR_LIST = listOf<Int>(
// RED FF0000
    Color.rgb(255, 0, 0),
// ORANGE FF8C00
    Color.rgb(255, 140, 0),
// YELLOW FFFF00
    Color.rgb(255, 255, 0),
// GREEN 00FF00
    Color.rgb(0, 255, 0),
// BLUE 0000FF
    Color.rgb(0, 0, 255),
// INDIGO 4B0082
    Color.rgb(75, 0, 130),
// PURPLE 800080
    Color.rgb(128, 0, 128),
// BURGUNDY 900020
    Color.rgb(144, 0, 32),
// PINK FFC0CB
    Color.rgb(255, 192, 203),
// GRAY 808080
    Color.rgb(128, 128, 128)
)

const val PAINT_TEXT_SIZE = 50f
const val TOP_TEXT_MARGIN = 20
const val BOTTOM_TEXT_MARGIN = 60

const val MIN_ROULETTE_SIZE = 2
const val MAX_ROULETTE_SIZE = 10