package com.example.customdrawingsampling.temp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomDrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawColor(Color.rgb(255, 255, 255))

        val redPaint = Paint()
        redPaint.color = Color.rgb(255, 0, 0)

        val greenPaint = Paint()
        greenPaint.color = Color.rgb(0, 255, 0)

        val bluePaint = Paint()
        bluePaint.color = Color.rgb(0, 0, 255)

        canvas.drawCircle(width * .75f, height * .2f, 200f, redPaint);
        canvas.drawCircle(width * .25f, height * .3f, 200f, greenPaint);
        canvas.drawCircle(width * .5f, height * .75f, 200f, bluePaint);
    }

}