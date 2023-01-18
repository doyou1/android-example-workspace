package com.example.customcomponentproject

import android.os.CountDownTimer
import android.util.Log

class ProgressCountDownTimer(
    private val millisFuture: Long,
    private val countDownInterval: Long,
    val addCount: () -> Unit,
    val moveToMain: () -> Unit
) : CountDownTimer(millisFuture, countDownInterval) {
    private val TAG = this::class.java.simpleName

    // `countDownInterval` ms마다 호출됨
    override fun onTick(millisUntilFinished: Long) {
        addCount()
    }

    override fun onFinish() {
        Log.e(TAG, "onFinish")
        moveToMain()
    }
}