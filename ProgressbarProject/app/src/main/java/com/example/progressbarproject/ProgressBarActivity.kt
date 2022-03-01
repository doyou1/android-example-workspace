package com.example.progressbarproject

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.progressbarproject.databinding.ActivityProgressBarBinding
import kotlin.math.roundToInt

class ProgressBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBarBinding
    private var inputSecond: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_progress_bar)

        // CountDownTimer 실행 (EditText의 값만큼 진행)
        binding.btnStart.setOnClickListener {
            if (binding.etCount.text.isNotEmpty()) {
                startLoadingBar()
            } else {
                Toast.makeText(this, "값을 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMoveToLoadingBar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun startLoadingBar() {
        inputSecond = binding.etCount.text.toString().toLong()   // EditText값을 타이머의 second(초)로 설정, inputType=number
        // CountDownTimer는 millisFuture(타이머 제한 초), countDownInterval(onTick 호출간격)을 millisecond로 받는다. -> second * 1000
        val countDownTimer = ProgressCountDownTimer(inputSecond * 1000, 1000, ::reset, ::setCount)
        binding.tvProgress.text = inputSecond.toString()
        binding.progressBar.max = inputSecond.toInt()
        countDownTimer.start()
    }

    private fun reset() {
        binding.tvProgress.text = ""
        binding.progressBar.progress = 0
    }

    private fun setCount(second: Int) {
        binding.tvProgress.text = second.toString()
        binding.progressBar.progress = (inputSecond - second).toInt()
    }
}

class ProgressCountDownTimer(private val millisFuture: Long, private val countDownInterval: Long, val reset: () -> Unit, val setCount: (Int) -> Unit) : CountDownTimer(millisFuture, countDownInterval) {
    private val TAG = "MyCountDownTimer"

    override fun onTick(millisUntilFinished: Long) {
        // 남은 millisecond를 파라미터로 제공해준다.
        // (millisUntilFinished / countDownInterval)을 통해 남은 `초`를 계산한다
        val reverseSecond = (millisUntilFinished / countDownInterval).toDouble().roundToInt()
        setCount(reverseSecond)
    }
    override fun onFinish() {
        Log.e(TAG, "onFinish")
        reset()
    }
}