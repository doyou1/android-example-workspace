package com.example.progressbarproject

import android.os.CountDownTimer
import android.util.Log
import kotlin.math.roundToInt

class ProgressCountDownTimer(
    millisFuture: Long,
    private val countDownInterval: Long,
    val reset: () -> Unit,
    val setCount: (Int) -> Unit
) : CountDownTimer(millisFuture, countDownInterval) {
    private val TAG = this::class.java.simpleName

    override fun onTick(millisUntilFinished: Long) {
        val reverseSecond = (millisUntilFinished / countDownInterval).toDouble().roundToInt()
        setCount(reverseSecond)
    }

    override fun onFinish() {
        Log.e(TAG, "onFinish")
        reset()
    }
}