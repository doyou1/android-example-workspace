package com.example.gamesampling.game

import android.graphics.Bitmap

class MiddleEnemyPlane(bitmap: Bitmap) : EnemyPlane(bitmap) {

    init {
        setPower(4)
        setValue(6000)
    }
}