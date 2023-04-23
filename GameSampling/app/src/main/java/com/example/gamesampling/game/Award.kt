package com.example.gamesampling.game

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

open class Award(bitmap: Bitmap) : AutoSprite(bitmap) {
    private var status: Int = STATUS_DOWN1

    init {
        setSpeed(7f)
    }

    override fun afterDraw(canvas: Canvas, paint: Paint, gameView: GameView) {
        if (!isDestroyed()) {
            val canvasHeight = canvas.height
            when (status) {
                STATUS_DOWN1 -> {
                    val maxY = getY() + getHeight()
                    if (maxY >= canvasHeight * 0.25) {
                        setSpeed(-5f)
                        status = STATUS_UP2
                    }
                }
                STATUS_UP2 -> {
                    val maxY = getY() + getHeight()
                    if (maxY + this.getSpeed() <= 0) {
                        setSpeed(13f)
                        status = STATUS_DOWN3
                    }
                }
                STATUS_DOWN3 -> {
                    if (getY() >= canvasHeight) {
                        destroy()
                    }
                }
            }
        }
    }

    companion object {
        const val STATUS_DOWN1 = 1
        const val STATUS_UP2 = 2
        const val STATUS_DOWN3 = 3
    }
}