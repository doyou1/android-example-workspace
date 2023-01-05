package com.example.customdrawingsampling

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomDrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawColor(Color.rgb(32, 32, 32))

        val redPaint = Paint()
        redPaint.setColor(Color.rgb(255, 0, 0))

        val greenPaint = Paint()
        greenPaint.setColor(Color.rgb(0, 255, 0))

        val bluePaint = Paint()
        bluePaint.setColor(Color.rgb(0, 0, 255))

        canvas.drawCircle(getWidth() * .75f, getHeight() * .2f, 200f, redPaint);
        canvas.drawCircle(getWidth() * .25f, getHeight() * .3f, 300f, greenPaint);
        canvas.drawCircle(getWidth() * .5f, getHeight() * .75f, 400f, bluePaint);
    }

}