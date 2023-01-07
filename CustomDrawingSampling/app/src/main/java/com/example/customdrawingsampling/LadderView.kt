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
    private val users = arrayListOf<XY>()
    private val goals = arrayListOf<XY>()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawColor(Color.rgb(255, 255, 255))

        val paint = Paint()
        paint.color = Color.rgb(0, 0, 0)

        for (item in ladders) {
            canvas.drawRect(item, paint)
        }

        for (item in regs) {
            canvas.drawRect(item, paint)
        }

        val textPaint = Paint()
        textPaint.color = Color.rgb(0, 0, 0)
        textPaint.textSize = 50f;
        textPaint.textAlign = Paint.Align.CENTER

        for (item in users) {
            canvas.drawText(item.name, item.x, item.y, textPaint)
        }

        for (item in goals) {
            canvas.drawText(item.name, item.x, item.y, textPaint)
        }
    }

    fun init(
        ladderSize: Int,
        ladderWidth: Int,
        regSize: Int,
        regHeight: Int,
        userNames: Array<String>,
        goalNames: Array<String>
    ) {
        ladders.clear()
        regs.clear()
        setLadders(ladderSize, ladderWidth)
        setRegs(regSize, regHeight)
        setUsers(userNames.iterator())
        setGoals(goalNames.iterator())
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

    private fun setRegs(regSize: Int, regHeight: Int) {
        val ladderTop = ladders[0].top
        val ladderBottom = ladders[0].bottom

        for (i in 0 until ladders.size - 1) {
            val currentRegSize = if (i % 2 == 0) {
                regSize
            } else {
                regSize - 1
            }

            val current = ladders[i]
            val after = ladders[i + 1]
            val space = ((ladderBottom - ladderTop) / (currentRegSize + 1))

            for (j in 1..currentRegSize) {
                if (Random.nextDouble() > 0.5) continue
                val regTop = ladderTop + (space * j)
                val regBottom = regTop + regHeight
                val regLeft = current.right
                val regRight = after.left
                val rect = Rect(regLeft, regTop, regRight, regBottom)
                regs.add(rect)
            }
        }
    }

    private fun setUsers(names: Iterator<String>) {
        val y = ladders[0].top - 20

        for (item in ladders) {
            val x = (item.left + item.right) / 2
            users.add(XY(names.next(), x.toFloat(), y.toFloat()))
        }
    }

    private fun setGoals(names: Iterator<String>) {
        val y = ladders[0].bottom + 60

        for (item in ladders) {
            val x = (item.left + item.right) / 2
            goals.add(XY(names.next(), x.toFloat(), y.toFloat()))
        }
    }
}