package com.example.gamesamplingv2.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.gamesamplingv2.MainCharacter

class JumpCharacterView : View {

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

    private val bitmaps = arrayListOf<Bitmap>()
    private lateinit var paint: Paint
    private var mainCharacter: MainCharacter? = null
    private var frame: Long = 0L
    private var isClick = false
    private var clickTimeY: Float = -1f
    private var isJumpUpTime = false
    private var isJumpDownTime = false

    private fun init(attrs: AttributeSet?, defStyle: Int) {
//        attrs?.let {
//            val typedArray: TypedArray =
//                context.obtainStyledAttributes(it, R.styleable.GameView, defStyle, 0)
//            typedArray.recycle()
//        }
        paint = Paint()
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { c ->
            mainCharacter?.let { m ->
                if (frame == 0L) {
                    m.centerTo(width / 2, height / 2)
                } else {
                    if (!isClick) {
                        m.move(1f, 0f)
                    }
                    if (isClick) {
                        if (m.getY() < clickTimeY - jumpHeight) {
                            isJumpUpTime = false
                            isJumpDownTime = true
                        }
                        if (isJumpUpTime) {
                            m.move(1f, -5f)
                        } else {
                            if (isJumpDownTime) {
                                m.move(1f, +5f)
                                if (m.getY() >= clickTimeY) {
                                    isJumpDownTime = false
                                    isClick = false
                                    clickTimeY = -1f
                                }
                            }
                        }
                    }
                }

                // out of canvas width - set to left side
                if (m.getX() > width) {
                    m.setX((-m.getWidth()).toFloat())
                }
                frame++
                m.draw(canvas, paint, this)
                postInvalidate()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { e ->
            Log.e("onTouch Event", "on touch")
            if (e.action == MotionEvent.ACTION_UP) {
                if (!isClick) {
                    mainCharacter?.let { m ->
                        isClick = true
                        isJumpUpTime = true
                        clickTimeY = m.getY()
                    }
                }
            }
        }

        return true
    }


    fun start(bitmapIds: Array<Int>) {
        for (bitmapId in bitmapIds) {
            val bitmap = BitmapFactory.decodeResource(resources, bitmapId)
            bitmaps.add(bitmap)
        }
        mainCharacter = MainCharacter(bitmaps[0])
        postInvalidate()
    }

    fun pause() {

    }

    fun destroy() {

    }

    companion object {
        const val jumpHeight = 200
    }
}