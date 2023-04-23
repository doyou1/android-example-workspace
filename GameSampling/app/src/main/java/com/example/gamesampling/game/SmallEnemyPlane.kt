package com.example.gamesampling.game

import android.graphics.Bitmap

class SmallEnemyPlane(bitmap: Bitmap) : EnemyPlane(bitmap) {

    init {
        setPower(1)
        setValue(1000)
    }
}