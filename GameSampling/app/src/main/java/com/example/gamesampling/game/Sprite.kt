package com.example.gamesampling.game

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import kotlin.math.round

open class Sprite(private var bitmap: Bitmap?) {

    private var visible: Boolean = true
    private var x: Float = 0f
    private var y: Float = 0f
    private var collideOffset: Float = 0f
    private var destroyed: Boolean = false
    private var frame: Int = 0

    fun getBitmap(): Bitmap? {
        return this.bitmap
    }

    fun setVisibility(visible: Boolean) {
        this.visible = visible
    }

    fun getVisibility(): Boolean {
        return this.visible
    }

    fun setX(x: Float) {
        this.x = x
    }

    fun getX(): Float {
        return this.x
    }

    fun setY(y: Float) {
        this.y = y
    }

    fun getY(): Float {
        return this.y
    }

    open fun getWidth(): Float {
        return bitmap!!.width.toFloat()
    }

    fun getHeight(): Int {
        return bitmap!!.height
    }

    fun move(offsetX: Float, offsetY: Float) {
        x += offsetX
        y += offsetY
    }

    fun moveTo(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun centerTo(centerX: Float, centerY: Float) {
        val w = getWidth()
        val h = getHeight()
        x = centerX - w / 2
        y = centerY - h / 2
    }

    fun getRectF(): RectF {
        val left = this.x
        val top = this.y
        val right = left + getWidth()
        val bottom = top + getHeight()
        return RectF(left, top, right, bottom)
    }

    open fun getBitmapSrcRec(): Rect {
        val rect = Rect()
        rect.left = 0
        rect.top = 0
        rect.right = getWidth().toInt()
        rect.bottom = getHeight()
        return rect
    }

    fun getCollideRectF(): RectF {
        val rectF = getRectF()
        rectF.left -= collideOffset
        rectF.right += collideOffset
        rectF.top -= collideOffset
        rectF.bottom += collideOffset
        return rectF
    }

    fun getCollidePointWithOther(s: Sprite): Point? {
        val rectF1 = getCollideRectF()
        val rectF2 = s.getCollideRectF()
        val rectF = RectF()
        val isIntersect = rectF.setIntersect(rectF1, rectF2)
        return if (isIntersect) {
            Point(round(rectF.centerX()).toInt(), round(rectF.centerY()).toInt())
        } else {
            null
        }
    }

    fun draw(canvas: Canvas, paint: Paint, gameView: GameView) {
        frame++
        beforeDraw(canvas, paint, gameView)
        onDraw(canvas, paint, gameView)
        afterDraw(canvas, paint, gameView)
    }

    open fun beforeDraw(canvas: Canvas, paint: Paint, gameView: GameView) {}

    open fun onDraw(canvas: Canvas, paint: Paint, gameView: GameView) {
        if (!destroyed && this.bitmap != null && getVisibility()) {
            val srcRef = getBitmapSrcRec()
            val dstRecF = getRectF()
            canvas.drawBitmap(bitmap!!, srcRef, dstRecF, paint)
        }
    }

    open fun afterDraw(canvas: Canvas, paint: Paint, gameView: GameView) {}

    fun destroy() {
        bitmap = null
        destroyed = true
    }

    fun isDestroyed(): Boolean {
        return destroyed
    }

    fun getFrame(): Int {
        return frame
    }
}