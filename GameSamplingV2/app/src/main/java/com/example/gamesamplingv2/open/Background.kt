package com.example.gamesamplingv2.open

import android.graphics.*
import com.example.gamesamplingv2.view.AutoBackgroundView


class Background(val bitmap: Bitmap) {

    private var visible: Boolean = true
    private var x: Float = 0f
    private var y: Float = 0f
    private var destroyed: Boolean = false

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

    fun draw(canvas: Canvas, paint: Paint, autoBackgroundView: AutoBackgroundView) {
//        frame++
        beforeDraw(canvas, paint, autoBackgroundView)
        onDraw(canvas, paint, autoBackgroundView)
        afterDraw(canvas, paint, autoBackgroundView)
    }

    open fun beforeDraw(canvas: Canvas, paint: Paint, autoBackgroundView: AutoBackgroundView) {}
    open fun onDraw(canvas: Canvas, paint: Paint, autoBackgroundView: AutoBackgroundView) {
        if (!destroyed && getVisibility()) {
            val srcRef = getBitmapSrcRec()
            val dstRecF = getRectF()
//            val cropBitmap = Bitmap.createScaledBitmap(bitmap, (canvas.width * 0.8).toInt(), (canvas.height * 0.8).toInt(), true)
//            val matrix = Matrix()
//            matrix.setScale(-1f, 1f)
//            val startX = sY * (canvas.width / 100);
//            val endX = eY * (canvas.width / 100);
//            val result = Bitmap.createBitmap(cropBitmap, 0, 0, width, height, matrix, true)
            canvas.drawBitmap(bitmap, srcRef, dstRecF, paint)
        }
    }

    open fun afterDraw(canvas: Canvas, paint: Paint, autoBackgroundView: AutoBackgroundView) {}
    fun setVisibility(visible: Boolean) {
        this.visible = visible
    }

    fun getVisibility(): Boolean = this.visible
    fun isDestroyed(): Boolean = this.destroyed
}