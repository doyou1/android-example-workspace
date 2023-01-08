package com.example.customdrawingsampling.roulette

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import kotlin.math.cos
import kotlin.math.sin

class RouletteView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val TAG = this::class.java.simpleName
    private var roulettes: List<Roulette>? = null
    private var isDone = false

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

        val textPaint = Paint()
        textPaint.apply {
            color = Color.BLACK
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }

        roulettes?.let {
            val sweepAngle = 360f / it.size.toFloat()
            val centerX = (rectF.left + rectF.right) / 2
            val centerY = (rectF.top + rectF.bottom) / 2
            val radius = (rectF.right - rectF.left) / 2 * 0.5

            for (i in it.indices) {
                val item = it[i]
                fillPaint.color = Color.parseColor(item.color)
                val startAngle = if (i == 0) 0f else sweepAngle * i
                canvas?.drawArc(rectF, startAngle, sweepAngle, true, fillPaint)

                val medianAngle = (startAngle + sweepAngle / 2f) * Math.PI / 180f
                val x = (centerX + (radius * cos(medianAngle))).toFloat()
                val y = (centerY + (radius * sin(medianAngle))).toFloat()
                canvas?.drawText(item.text, x, y, textPaint)
            }
        }

    }

    fun setRoulette(list: List<Roulette>) {
        roulettes = list
        invalidate()
    }

    fun rotateRoulette(toDegrees: Float, duration: Long, rotateListener: RotateListener) {
        val animListener = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                rotateListener.onRotateStart()
            }

            override fun onAnimationEnd(animation: Animation?) {
                rotateListener.onRotateEnd(getRouletteRotateResult(toDegrees))
                isDone = true
                invalidate()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        }
        val rotateAnim = RotateAnimation(
            0f, toDegrees,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnim.duration = duration
        rotateAnim.fillAfter = true
        rotateAnim.setAnimationListener(animListener)

        startAnimation(rotateAnim)
    }

    private fun getRouletteRotateResult(degrees: Float): String {
        val moveDegrees = degrees % 360
        val resultAngle = if (moveDegrees > 270) 360 - moveDegrees + 270 else 270 - moveDegrees
        roulettes?.let {
            for (i in 1..it.size) {
                if (resultAngle < (360 / it.size) * i) {
                    return it[i - 1].text
                }
            }
        }
        return ""
    }

    fun stopRotateRoulette() {
//        rotateAnim?.cancel()
//        clearAnimation()
    }


}