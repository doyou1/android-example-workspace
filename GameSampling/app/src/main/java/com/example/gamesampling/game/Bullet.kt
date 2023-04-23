package com.example.gamesampling.game

import android.graphics.Bitmap

class Bullet(bitmap: Bitmap) : AutoSprite(bitmap) {

    init {
        setSpeed(-10f)
    }
}