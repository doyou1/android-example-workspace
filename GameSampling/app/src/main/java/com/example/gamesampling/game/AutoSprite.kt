package com.example.gamesampling.game

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

open class AutoSprite(bitmap: Bitmap) : Sprite(bitmap) {

    private var speed: Float = 2f

    fun setSpeed(speed: Float) {
        this.speed = speed
    }

    fun getSpeed(): Float = this.speed

    override fun beforeDraw(canvas: Canvas, paint: Paint, gameView: GameView) {
//        super.beforeDraw(canvas, paint, gameView)
        if (!isDestroyed()) {
            move(0f, speed * gameView.getDensity())
        }
    }

    override fun afterDraw(canvas: Canvas, paint: Paint, gameView: GameView) {
        if (!isDestroyed()) {
            val canvasRecF = RectF(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat())
            val spriteRecF = getRectF()
            if (!RectF.intersects(canvasRecF, spriteRecF)) {
                destroy()
            }
        }
    }
}