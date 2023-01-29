package com.example.progressbarproject

import android.os.CountDownTimer
import android.util.Log
import kotlin.math.roundToInt

class LoadingCountDownTimer(
    millisFuture: Long,
    private val countDownInterval: Long,
    val hideProgressBar: () -> Unit,
    val setCountText: (Int) -> Unit
) : CountDownTimer(millisFuture, countDownInterval) {
    private val TAG = this::class.java.simpleName

    override fun onTick(millisUntilFinished: Long) {
        val second = (millisUntilFinished / countDownInterval).toDouble().roundToInt()
        setCountText(second)
    }

    override fun onFinish() {
        Log.e(TAG, "onFinish")
        hideProgressBar()
    }
}