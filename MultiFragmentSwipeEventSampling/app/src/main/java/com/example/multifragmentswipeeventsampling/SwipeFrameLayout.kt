package com.example.multifragmentswipeeventsampling

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.FrameLayout
import kotlin.math.abs

class SwipeFrameLayout : FrameLayout, GestureDetector.OnGestureListener {

    private val TAG = this::class.java.simpleName
    private var gestureDetector: GestureDetector? = null

    private val SWIPE_THRESHOLD = 50
    private val SWIPE_VELOCITY_THRESHOLD = 100

    constructor(context: Context) : super(context) {
        Log.e(TAG, "context")
        gestureDetector = GestureDetector(this)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        Log.e(TAG, "context, attrs")
        gestureDetector = GestureDetector(this)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        Log.e(TAG, "context, attrs, defStyleAttr")
        gestureDetector = GestureDetector(this)
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        gestureDetector?.onTouchEvent(event)
//        return false
////        return super.onTouchEvent(event)
//    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.e(TAG, "front")
        gestureDetector?.onTouchEvent(ev)
        Log.e(TAG, "back")

        return false

    }

    override fun onFling(
        downEvent: MotionEvent?,
        moveEvent: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.e(TAG, "onFling")
        var result = false

        moveEvent?.let {
            downEvent?.let {
                val diffY = moveEvent!!.y - downEvent!!.y
                val diffX = moveEvent!!.x - downEvent!!.x

                if (abs(diffX) > abs(diffY)) {
                    // right or left swipe
                    if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                } else {
                    // up or down swipe
                    if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
//                            onSwipeBottom()
                        } else {
//                            onSwipeTop()
                        }
                        result = true
                    }
                }
            }
        }

        return result
    }

    private fun onSwipeLeft() {
        Log.e(TAG, "onSwipeLeft")
//        changeDate(DateUtil.getNextMonthDate(date))
    }

    private fun onSwipeRight() {
        Log.e(TAG, "onSwipeRight")

//        changeDate(DateUtil.getPrevMonthDate(date))
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {}
    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {}
}