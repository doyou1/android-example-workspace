package com.example.customdrawingsampling

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.abs
import kotlin.random.Random

class LadderView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val TAG = this::class.java.simpleName
    private val ladders = arrayListOf<Rect>()
    private val regs = arrayListOf<Rect>()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawColor(Color.rgb(255, 255, 255))

        val paint = Paint()
        for (item in ladders) {
            paint.color =
                Color.rgb(
                    (Math.random() * 256).toInt(),
                    (Math.random() * 256).toInt(),
                    (Math.random() * 256).toInt()
                )
            canvas.drawRect(item, paint)
        }

        for (item in regs) {
            paint.color =
                Color.rgb(
                    (Math.random() * 256).toInt(),
                    (Math.random() * 256).toInt(),
                    (Math.random() * 256).toInt()
                )
            canvas.drawRect(item, paint)
        }
    }

    fun init(ladderSize: Int, ladderWidth: Int, legSize: Int, legHeight: Int) {
        setLadders(ladderSize, ladderWidth)
        setLegs(legSize, legHeight)
        invalidate()
    }

    private fun setLadders(ladderSize: Int, ladderWidth: Int) {
        val blankWidth = (width - (ladderWidth * ladderSize)) / (ladderSize + 1)
        val ladderTop = (height * 0.1).toInt()
        val ladderBottom = (height * 0.9).toInt()
        for (i in 0 until ladderSize) {
            val ladderLeft = blankWidth * (i + 1)
            val ladderRight = ladderLeft + ladderWidth
            val rect = Rect(ladderLeft, ladderTop, ladderRight, ladderBottom)
            ladders.add(rect)
        }
    }

    private fun setLegs(legSize: Int, legHeight: Int) {
        val ladderTop = ladders[0].top
        val ladderBottom = ladders[0].bottom

        for (i in 0 until ladders.size - 1) {
            if(i % 2 == 0) {
                val currentLegSize = legSize

                val current = ladders[i]
                val after = ladders[i + 1]
                val space = ((ladderBottom - ladderTop) / (currentLegSize + 1))

                for (j in 1..currentLegSize) {
                    if(Random.nextDouble() > 0.5) continue
                    val legTop = ladderTop + (space * j)
                    val legBottom = legTop + legHeight
                    val legLeft = current.right
                    val legRight = after.left
                    val rect = Rect(legLeft, legTop, legRight, legBottom)
                    regs.add(rect)
                }
            }
            else {
                val currentLegSize = legSize - 1

                val current = ladders[i]
                val after = ladders[i + 1]
                val space = ((ladderBottom - ladderTop) / (currentLegSize + 1))

                for (j in 1..currentLegSize) {
                    if(Random.nextDouble() > 0.5) continue
                    val legTop = ladderTop + (space * j)
                    val legBottom = legTop + legHeight
                    val legLeft = current.right
                    val legRight = after.left
                    val rect = Rect(legLeft, legTop, legRight, legBottom)
                    regs.add(rect)
                }
            }



        }
    }
}