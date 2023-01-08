package com.example.customdrawingsampling.roulette

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.customdrawingsampling.MIN_ROULETTE_SIZE
import kotlin.random.Random

class RouletteView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val TAG = this::class.java.simpleName
    var rouletteSize = MIN_ROULETTE_SIZE

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val rectF = drawStroke(canvas)
        drawRoulette(canvas, rectF)
    }

    private fun drawStroke(canvas: Canvas?): RectF {
        val strokePaint = Paint()
        strokePaint.color = Color.BLACK
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = 15f
        strokePaint.isAntiAlias = true

        val radius = height * 0.4

        val rectLeft = (width / 2) - radius
        val rectRight = (width / 2) + radius
        val rectTop = (height / 2) - radius
        val rectBottom = (height / 2) + radius

        val rectF =
            RectF(rectLeft.toFloat(), rectTop.toFloat(), rectRight.toFloat(), rectBottom.toFloat())

        canvas?.drawArc(rectF, 0f, 360f, true, strokePaint)

        return rectF
    }

    private fun drawRoulette(canvas: Canvas?, rectF: RectF) {
        val fillPaint = Paint()
        fillPaint.style = Paint.Style.FILL
        fillPaint.isAntiAlias = true

        val sweepAngle = 360f / rouletteSize.toFloat()

        val colors = arrayListOf<String>()
        for (i in 0 until rouletteSize) {
            var color = ""
            while (true) {
                color = getRandomColor()
                if (colors.contains(color)) continue
                else {
                    colors.add(color)
                    break
                }
            }
            fillPaint.color = Color.parseColor(color)
            val startAngle = if (i == 0) 0f else sweepAngle * i

            canvas?.drawArc(rectF, startAngle, sweepAngle, true, fillPaint)
            Roulette(startAngle, sweepAngle, color)
        }
    }

    fun setSize(size: Int) {
        rouletteSize = size
        invalidate()
    }

    private fun getRandomColor(): String {
        val r = String.format("%02X", Random.nextInt(0, 256))
        val g = String.format("%02X", Random.nextInt(0, 256))
        val b = String.format("%02X", Random.nextInt(0, 256))

        return "#$r$g$b"
    }
}