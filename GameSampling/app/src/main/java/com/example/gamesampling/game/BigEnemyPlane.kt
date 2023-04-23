package com.example.gamesampling.game

import android.graphics.Bitmap

class BigEnemyPlane(bitmap: Bitmap) : EnemyPlane(bitmap) {

    init {
        setPower(10)
        setValue(30000)
    }
}