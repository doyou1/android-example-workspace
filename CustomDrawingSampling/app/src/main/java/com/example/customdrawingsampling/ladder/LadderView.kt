package com.example.customdrawingsampling.ladder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.example.customdrawingsampling.*
import kotlin.random.Random

class LadderView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val TAG = this::class.java.simpleName
    private val ladders = arrayListOf<Rect>()
    private val regs = arrayListOf<Rect>()
    private val users = arrayListOf<XY>()
    private val goals = arrayListOf<XY>()
    private val results = arrayListOf<Result>()

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
        textPaint.textSize = PAINT_TEXT_SIZE;

        for (item in users) {
            textPaint.textAlign = Paint.Align.CENTER
            canvas.drawText(item.name, item.x, item.y, textPaint)
        }

        for (item in goals) {
            textPaint.textAlign = Paint.Align.CENTER
            canvas.drawText(item.name, item.x, item.y, textPaint)
        }

        for (item in results) {
            paint.color = item.color
            canvas.drawRect(item.rect, paint)
        }
    }

    fun init(
        ladderSize: Int,
        ladderWidth: Int,
        userNames: Array<String>,
        goalNames: Array<String>
    ) {
        ladders.clear()
        setLadders(ladderSize, ladderWidth)
        setUsers(userNames.iterator())
        setGoals(goalNames.iterator())
        invalidate()
    }

    fun makeRegs(
        regSize: Int,
        regHeight: Int
    ) {
        regs.clear()
        results.clear()
        setRegs(regSize, regHeight)
        invalidate()
    }

    fun showAllResult() {
        results.clear()
        val colorList = COLOR_LIST.toList().iterator()

        for (ladder in ladders) {
            val color = colorList.next()
            addResults(ladder, color)
        }
        invalidate()
    }

    fun showResult(position: Int) {
        results.clear()
        val color = COLOR_LIST[position]
        val ladder = ladders[position]
        addResults(ladder, color)
        invalidate()
    }

    private fun addResults(ladder: Rect, color: Int) {
        var currentTop = ladder.top
        var currentLeft = ladder.left
        val currentBottom = ladder.bottom
        var currentRight = ladder.right
        val ladderWidth = ladder.right - ladder.left
        var localRegs = regs.toList()


        while (true) {
            val currentRegs =
                localRegs.filter { reg ->
                    currentTop < reg.top && (reg.left == currentRight || reg.right == currentLeft)
                }.sortedBy { reg -> reg.top }

            if (currentRegs.isEmpty()) {
                results.add(
                    Result(
                        color,
                        Rect(currentLeft, currentTop, currentRight, currentBottom)
                    )
                )
                break
            }

            val currentReg = currentRegs[0]
            results.add(
                Result(
                    color,
                    Rect(currentLeft, currentTop, currentRight, currentReg.bottom)
                )
            )
            results.add(Result(color, currentRegs[0]))
            currentTop = currentReg.top

            if (currentRight == currentReg.left) {
                currentLeft = currentReg.right
                currentRight = currentLeft + ladderWidth
            } else if (currentLeft == currentReg.right) {
                currentRight = currentReg.left
                currentLeft = currentRight - ladderWidth
            }
            localRegs =
                localRegs.filter { reg -> !(currentReg.left == reg.left && currentReg.right == reg.right && currentReg.top == reg.top && currentReg.bottom == reg.bottom) }
        }
    }

    private fun setLadders(ladderSize: Int, ladderWidth: Int) {
        val blankWidth = (width - (ladderWidth * ladderSize)) / (ladderSize + 1)
        val ladderTop = (height * 0.1).toInt()
        val ladderBottom = (height * 0.8).toInt()
        for (i in 0 until ladderSize) {
            val ladderLeft = blankWidth * (i + 1) + (ladderWidth * i)
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
        val y = ladders[0].top - TOP_TEXT_MARGIN

        for (item in ladders) {
            val x = (item.left + item.right) / 2
            users.add(XY(names.next(), x.toFloat(), y.toFloat()))
        }
    }

    private fun setGoals(names: Iterator<String>) {
        val y = ladders[0].bottom + BOTTOM_TEXT_MARGIN

        for (item in ladders) {
            val x = (item.left + item.right) / 2
            goals.add(XY(names.next(), x.toFloat(), y.toFloat()))
        }
    }
}