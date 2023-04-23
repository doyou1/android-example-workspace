package com.example.gamesamplingv2

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF

open class Character(private val bitmap: Bitmap) {

    private var visible: Boolean = true
    private var x: Float = 0f
    private var y: Float = 0f
    private var destroyed: Boolean = false
    private var frame: Int = 0

    fun getWidth(): Int = bitmap.width
    fun getHeight(): Int = bitmap.height
    fun setX(x: Float) {
        this.x = x
    }

    fun getX(): Float = this.x
    fun setY(y: Float) {
        this.y = y
    }

    fun getY(): Float = this.y
    fun move(offsetX: Float, offsetY: Float) {
        x += offsetX
        y += offsetY
    }

    fun getBitmapSrcRec(): Rect {
        val rect = Rect()
        rect.left = 0
        rect.top = 0
        rect.right = getWidth()
        rect.bottom = getHeight()
        return rect
    }

    fun getRectF(): RectF {
        val left = this.x
        val top = this.y
        val right = left + getWidth()
        val bottom = top + getHeight()
        return RectF(left, top, right, bottom)
    }

    fun centerTo(centerX: Int, centerY: Int) {
        this.x = (centerX - (getWidth() / 2)).toFloat()
        this.y = (centerY - (getHeight() / 2)).toFloat()
    }

    fun draw(canvas: Canvas, paint: Paint, gameView: GameView) {
//        frame++
        beforeDraw(canvas, paint, gameView)
        onDraw(canvas, paint, gameView)
        afterDraw(canvas, paint, gameView)
    }

    open fun beforeDraw(canvas: Canvas, paint: Paint, gameView: GameView) {}
    open fun onDraw(canvas: Canvas, paint: Paint, gameView: GameView) {
        if (!destroyed && getVisibility()) {
            val srcRef = getBitmapSrcRec()
            val dstRecF = getRectF()
            canvas.drawBitmap(bitmap, srcRef, dstRecF, paint)
        }
    }

    open fun afterDraw(canvas: Canvas, paint: Paint, gameView: GameView) {}
    fun setVisibility(visible: Boolean) {
        this.visible = visible
    }

    fun getVisibility(): Boolean = this.visible
    fun isDestroyed(): Boolean = this.destroyed
}