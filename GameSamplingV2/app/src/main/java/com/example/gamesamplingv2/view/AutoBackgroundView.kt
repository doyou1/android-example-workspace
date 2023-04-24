package com.example.gamesamplingv2.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.gamesamplingv2.open.Background


class AutoBackgroundView : View {

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private lateinit var paint: Paint
    private var backGround: Background? = null
    private var frame = 0L

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        paint = Paint()
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { c ->
            backGround?.let { bg ->
                if (frame == 0L) {
                    bg.setX(0f)
                    bg.setY(0f)
                }
                frame++

                val crop = Bitmap.createBitmap(
                    bg.bitmap,
                    ((frame * 10) % (bg.bitmap.width - bg.bitmap.height)).toInt(),
                    0,
                    bg.bitmap.height,
                    bg.bitmap.height
                )
                val result = Bitmap.createScaledBitmap(crop, canvas.width, canvas.height, false);
                canvas.drawBitmap(result, 0f, 0f, paint)
                postInvalidate()
            }
        }
    }

    fun start(bgBitmapId: Int, maxSize: Int) {
        val bgBitmap = resizeBitmap(BitmapFactory.decodeResource(resources, bgBitmapId), maxSize)
        backGround = Background(bgBitmap)
        postInvalidate()
    }

    fun pause() {

    }

    fun destroy() {

    }

    fun resizeBitmap(source: Bitmap, maxSize: Int): Bitmap {
        var width = source.width
        var height = source.height

        val bitmapRatio = width.toFloat() / height.toFloat()

        if (bitmapRatio > 1) {
            width = maxSize;
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize;
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(source, width, height, true)
    }
}