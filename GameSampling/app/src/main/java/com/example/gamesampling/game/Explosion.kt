package com.example.gamesampling.game

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Explosion(bitmap: Bitmap) : Sprite(bitmap) {

    private var segment: Int = 14
    private var level: Int = 0
    private var explodeFrequency: Int = 2

    override fun getWidth(): Float {
        val bitmap = getBitmap()
        return if (bitmap != null) {
            (bitmap!!.width / segment).toFloat()
        } else {
            0f
        }
    }

    override fun getBitmapSrcRec(): Rect {
        val rect = super.getBitmapSrcRec()
        val left = (level * getWidth()).toInt()
        rect.offset(left, 0)
        return rect
    }

    override fun afterDraw(canvas: Canvas, paint: Paint, gameView: GameView) {
        if (!isDestroyed()) {
            if (getFrame() % explodeFrequency == 0) {
                level++
                if (level >= segment) {
                    destroy()
                }
            }
        }
    }

    fun getExplodeDurationFrame(): Int = segment * explodeFrequency

}