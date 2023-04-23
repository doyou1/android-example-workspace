package com.example.gamesamplingv2

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class GameView : View {

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
                m.centerTo(c.width / 2, c.height / 2)
                m.draw(canvas, paint, this)
            }
        }
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

    private fun getWidthSum(list: List<Bitmap>): Int {
        var sum = 0
        for (item in list) {
            sum += item.width
        }
        return sum
    }

    private fun getHeightSum(list: List<Bitmap>): Int {
        var sum = 0
        for (item in list) {
            sum += item.height
        }
        return sum
    }
}