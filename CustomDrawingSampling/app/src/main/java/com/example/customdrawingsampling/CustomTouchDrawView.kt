package com.example.customdrawingsampling

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CustomTouchDrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val points = arrayListOf<Point>()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawColor(Color.rgb(32, 32, 32))

        for (p in points) {
            val paint = Paint()
            paint.color = Color.rgb(
                (Math.random() * 256).toInt(),
                (Math.random() * 256).toInt(),
                (Math.random() * 256).toInt()
            )
            canvas!!.drawCircle(p.x.toFloat(), p.y.toFloat(), 50f, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (event != null) {
            points.add(Point(event.x.toInt(), event.y.toInt()))
            invalidate()
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    fun clear() {
        points.clear()
        invalidate()
    }
}